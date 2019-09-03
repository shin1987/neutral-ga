#!/usr/bin/env python3
import sys
import numpy as np
import matplotlib.pyplot as plt

problem = np.genfromtxt(sys.argv[1], delimiter = ',')

with open(sys.argv[2]) as fin:
    textSolution = fin.readline()
solution = textSolution.split('-')
for i in range(0, len(solution)):
    solution[i] = int(solution[i])

fig, axs = plt.subplots(1, 1)

axs.set_aspect('equal', 'box')

path = np.zeros((len(solution) + 1, 2), dtype = float)
for i in range(0, len(solution)):
    path[i, :] = problem[solution[i], :]
path[-1, :] = problem[solution[0], :]
axs.plot(path[:, 0], path[:, 1], color='red')
axs.scatter(problem[:, 0], problem[:, 1], color='blue')
fig.tight_layout()
plt.axis('off')
fig.savefig(sys.argv[3])



