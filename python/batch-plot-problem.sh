#!/bin/bash

prefix=$1

for i in $(seq $2 $3)
do
    plotProblem.py ../dataset/$prefix-$i.csv $prefix-$i.png
    plotSolution.py ../dataset/$prefix-$i.csv ../dataset/$prefix-$i-solution.txt $prefix-$i-solution.png
done

