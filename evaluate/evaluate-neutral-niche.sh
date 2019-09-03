#!/bin/bash
output_file="result.log"
seed=0
problem_set=xing.problem.CircularProblem
while (( $# ))
do
    case $1 in
        --output)
            shift
            output_file=$1
            ;;
        --seed)
            shift
            seed=$1
            ;;
        --problem-set)
            shift
            problem_set=$1
            ;;
    esac
    shift
done

script_dir=$(dirname $0)
export PATH="$PATH:$PWD/$script_dir"
pushd . > /dev/null
cd "$script_dir/../java/build" > /dev/null
build_dir="$PWD"
popd > /dev/null
echo $build_dir

program_option="xing.app.text.TextRunner"
program_option+=" $problem_set xing.algorithm.StochasticNicheAlgorithm"
program_option+=" --problem-args --problem-size,200,--circles,3"

(
    for death_count in $(seq 1 10)
    do
        death_rate=$(calc '*' $death_count 0.1 | sed -E 's/(\.)*0+$//g')
        # population * death_rate * 2 == raw_evaluation
        # Standard evaluation = population * 10000 * 0.75
        # Iteration std evaluation / raw_evaluation = 10000 * 0.375 / death_rate
        iteration=$(calc '+' $(calc '/' 3750 $death_rate) 0.5 \
                        | sed -E 's/\..+//g')
        
        for replace_count in $(seq 0 9)
        do
            replace_rate=$(calc '*' $replace_count 0.1)
            for mutation_count in $(seq 0 10)
            do
                echo "Updating...$death_count $replace_count $mutation_count" \
                     >> /tmp/xing.log
                mutation_rate=$(calc '*' $mutation_count 0.1)
                test $(calc '>' \
                              $(calc '+' $mutation_rate $replace_rate) 1.0) \
                     -gt 0 && break
                migration_rate=$(calc '-' 1.0 \
                                      $(calc '+' $replace_rate $mutation_rate))
                echo "===================="
                echo Iteration: $iteration
                echo Death rate: $death_rate
                echo Replace rate: $replace_rate
                echo Mutation rate: $mutation_rate
                echo Migration rate: $migration_rate

                config=--sampler,stochastic
                config+=,--algorithm-seed,$seed
                config+=,--replace-rate,$replace_rate
                config+=,--death-rate,$death_rate
                config+=,--mutation-rate,$mutation_rate
                config+=,--migration-rate,$migration_rate
                config+=,--iteration,$iteration
                java -cp "$build_dir" $program_option --algorithm-args $config
           done
        done
    done
) > $output_file

echo "Execution finished"| mail -s "Project result" -a $output_file sc18xy@leeds.ac.uk
