import matplotlib.pyplot as plt
import sys

x =  [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], []]
y = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], []]
letters = []

markers = ["$A$","$B$","$C$","$D$","$E$","$F$","$G$","$H$","$I$","$J$","$K$","$L$",
"$M$","$N$","$O$","$P$","$Q$","$R$","$S$","$T$","$U$","$V$","$W$","$X$","$Y$","$Z$"]

with open(sys.argv[1], "r") as reader:
    lines = reader.readlines()
  

f=open("data.txt","r") 
for line in f:
    a = line.strip('\n')
    b = a.split(' ')
    place = ord(b[0]) - ord('A')
    x[place].append(float(b[1]))
    y[place].append(float(b[2]))

maxesX = []
for i in range(26):    
    if(len(x[i]) != 0):
        maxesX.append(max(x[i]))
maxesY = []
for i in range(26):    
    if(len(y[i]) != 0):
        maxesY.append(max(y[i]))

x_max = max(maxesX)  + 0.2*max(maxesX)
y_max = max(maxesY) + 0.2*max(maxesY)

fig = plt.figure()

plt.Axes.set_frame_on
plt.title("Error - Epoch")
for i in range(26):
    enumerate(markers)
    plt.plot(x[i],y[i], marker=markers[i],lw=0)


plt.ylabel('error')
plt.xlabel('epoch')
plt.grid(True)
plt.xlim([- 0.2*x_max, x_max])
plt.ylim([- 0.2*y_max, y_max])
plt.savefig('Cluster.png')
plt.show()
