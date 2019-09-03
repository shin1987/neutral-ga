#!/usr/bin/env python3
import sys
import matplotlib.pyplot as plt
import numpy as np


problem = np.genfromtxt(sys.argv[1], delimiter = ',')
fig, axs = plt.subplots(1, 1)
axs.scatter(problem[:, 0], problem[:, 1], color='blue')
axs.set_aspect('equal', 'box')
fig.tight_layout()
plt.axis('off')
fig.savefig(sys.argv[2])

