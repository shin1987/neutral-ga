#!/bin/bash
export PATH=$PATH:$PWD/../evaluate

set -m

function block()
{
    while [ 1 ]
    do
        fg 2> /dev/null;
        [ $? == 1 ] && break;
    done
}

problem='--problem-set xing.problem.CircularProblem'
evaluate-neutral-niche.sh $problem --seed 0 --output Circular.0.log &
evaluate-neutral-niche.sh $problem --seed 1 --output Circular.1.log &
evaluate-neutral-niche.sh $problem --seed 2 --output Circular.2.log &
evaluate-neutral-niche.sh $problem --seed 3 --output Circular.3.log &

block
echo "Circular finished"

problem='--problem-set xing.problem.Spiral'
evaluate-neutral-niche.sh $problem --seed 0 --output Spiral.0.log &
evaluate-neutral-niche.sh $problem --seed 1 --output Spiral.1.log &
evaluate-neutral-niche.sh $problem --seed 2 --output Spiral.2.log &
evaluate-neutral-niche.sh $problem --seed 3 --output Spiral.3.log &

block
echo "Spiral finished"

problem='--problem-set xing.problem.FuzzyRing'
evaluate-neutral-niche.sh $problem --seed 0 --output FuzzyRing.0.log &
evaluate-neutral-niche.sh $problem --seed 1 --output FuzzyRing.1.log &
evaluate-neutral-niche.sh $problem --seed 2 --output FuzzyRing.2.log &
evaluate-neutral-niche.sh $problem --seed 3 --output FuzzyRing.3.log &

block
echo "FuzzyRing finished"
