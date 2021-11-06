#! /usr/bin/gnuplot
#
# Plot a single point with Gnuplot without using a data file
#
# AUTHOR: Hagen Wierstorf

reset

# wxt
set terminal wxt size 350,262 enhanced font 'Verdana,10' persist
# png
#set terminal pngcairo size 350,262 enhanced font 'Verdana,10'
#set output 'single_point.png'
# svg
#set terminal svg size 350,262 fname 'Verdana, Helvetica, Arial, sans-serif' \
#fsize '10'
#set output 'single_point.svg'

# Styling
set border linewidth 1.5
set pointsize 0.00002
set style line 1 lc rgb 'purple' pt 'A'   
set style line 2 lc rgb 'blue' pt 'B'   
set style line 3 lc rgb 'black' pt 'C'   
set style line 4 lc rgb 'red' pt 'D'   
set style line 5 lc rgb 'orange' pt 'E'   
set style line 6 lc rgb 'grey' pt 'F'  
set style line 7 lc rgb 'cyan' pt 'G'   
set style line 8 lc rgb 'yellow' pt 'H'   
set style line 9 lc rgb 'green' pt 'I'  
set style line 10 lc rgb 'purple' pt 'J'   
set style line 11 lc rgb 'brown' pt 'K'   
set style line 12 lc rgb 'pink' pt 'L'  
set style line 13 lc rgb 'olive' pt 'M'   
set style line 14 lc rgb 'honeydew' pt 'N'   
set style line 15 lc rgb 'bisque' pt 'O'  
set style line 16 lc rgb 'gold' pt 'P'   
set style line 17 lc rgb 'coral' pt 'Q'   
set style line 18 lc rgb 'beige' pt 'R'  
set style line 19 lc rgb 'violet' pt 'S'   
set style line 20 lc rgb 'sandybrown' pt 'T'   
set style line 21 lc rgb 'chartreuse' pt 'U'  
set style line 22 lc rgb 'light-magenta' pt 'V'   
set style line 23 lc rgb 'dark-green' pt 'W'   
set style line 24 lc rgb 'lemonchiffon' pt 'X'  
set style line 25 lc rgb 'sienna1' pt 'Y'   
set style line 25 lc rgb 'salmon' pt 'Z'   
unset key


set yrange[-20:120]
set xrange[-20:120]
set grid


plot 'A' w p ls 1 , 'B' w p ls 2, 'C' w p ls 3, 'D' w p ls 4, 'E' w p ls 5, 'F' w p ls 6, 'G' w p ls 7, 'H' w p ls 8, 'I' w p ls 9, 'J' w p ls 10, 'K' w p ls 11, 'L' w p ls 12, 'M' w p ls 13, 'N' w p ls 14, 'O' w p ls 15, 'P' w p ls 16, 'Q' w p ls 17, 'R' w p ls 18, 'S' w p ls 19, 'T' w p ls 20, 'U' w p ls 21, 'V' w p ls 22, 'W' w p ls 23, 'X' w p ls 24, 'Y' w p ls 25, 'Z' w p ls 26

