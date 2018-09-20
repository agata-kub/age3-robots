import os
import sys
from collections import OrderedDict

rootdir = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-19/4Floors-4-100-16-time'
# rootdir ='C:/Agata/agh/mgr/test'
factor = 1
raw = 'raw'
processed = 'processed'
interpolation = 'interpolation'

# extract all data with [S] tag from files in 'raw' directory, and put it into separate .csv files, in 'processed' directory
# CURRENTLY NOT IN USE
def extract_S_data():
    for subdir, dirs, files in os.walk(rootdir+'/'+raw):
        for file in files:
            infile = open(rootdir+'/'+raw+'/'+file,'r')
            outfile = open(rootdir+'/'+processed+'/'+file,'w')
            lines = infile.readlines()
            for line in lines:
                if line.startswith("[S]"):
                    data = line.split(';')
                    resultline = data[1]+','+data[2]+'\n'
                    outfile.write(resultline)
            infile.close()
            outfile.close()

# extract all data with [S] tag from all files in raw directory, snaps its fitness to buckets and puts into one separate file            
# CURRENTLY NOT IN USE
def extract_S_data_and_snap_fitness():
    outfile = open(rootdir+'/snapped.csv','w')
    outfile.write("EVALUATION_COUNTER,CURRENT_BEST_FITNESS\n")
    for subdir, dirs, files in os.walk(rootdir+'/'+raw):
        for file in files:
            infile = open(rootdir+'/'+raw+'/'+file,'r')
            lines = infile.readlines()
            for line in lines:
                if line.startswith("[S]"):
                    data = line.split(';')
                    fitness_evaluations = (int(data[3])//100)*100
                    resultline = str(fitness_evaluations)+','+data[2]+'\n'
                    outfile.write(resultline)
            infile.close()
    outfile.close()


# accepts the file created by extract_S_data_and_snap_fitness(), and transformes duplicate entries into average value
# CURRENTLY NOT IN USE
def map_to_avg():
    infile = open(rootdir+'/snapped.csv','r')
    outfile = open(rootdir+'/snapped_avg.csv','w')
    lines = infile.readlines()
    snapped_fitness_to_current_best_list = {}
    for line in lines:
        if not line.startswith("E"):
            data = line.split(',')
            if data[0] in snapped_fitness_to_current_best_list:
                snapped_fitness_to_current_best_list[data[0]].append(float(data[1]))
            else:
                snapped_fitness_to_current_best_list[data[0]] = [float(data[1])]
    for snapped_fitness in snapped_fitness_to_current_best_list:
        current_best_avg = sum(snapped_fitness_to_current_best_list[snapped_fitness])/len(snapped_fitness_to_current_best_list[snapped_fitness])
        outfile.write(snapped_fitness+','+str(current_best_avg)+'\n')
    outfile.close()
    infile.close()


# extract best solution's fitness from all files and put them into one .csv file
# CURRENTLY NOT IN USE
def prepare_table():
    outfile = open(rootdir+'/'+processed+'/all.csv','w')
    outfile.write("NUMBER,FITNESS\n")
    counter = 0
    for subdir, dirs, files in os.walk(rootdir+'/'+raw):
        for file in files:
            infile = open(rootdir+'/'+raw+'/'+file,'r')
            lines = infile.readlines()
            lines.reverse()
            for line in lines:
                if line.startswith("[S]"):
                    data = line.split(';')
                    resultline = str(counter)+','+data[2]+'\n'
                    outfile.write(resultline)
                    infile.close()
                    break
            counter += 1
    outfile.close()

# CURRENTLY NOT IN USE
def generate_for_boxplot():
    outfile = open(rootdir+'/'+processed+'/boxplot.csv','w')
    outfile.write("TIME,FITNESS\n")
    for subdir, dirs, files in os.walk(rootdir+'/'+raw):
        for file in files:
            counter = 0
            infile = open(rootdir+'/'+raw+'/'+file,'r')
            lines = infile.readlines()
            for line in lines:
                if line.startswith("[S]"):
                    data = line.split(';')
                    resultline = str(counter)+','+data[2]+'\n'
                    outfile.write(resultline)
                    counter += 1
            infile.close()
    outfile.close()

#################################################################################

# extract all data with [S] tag from all files in raw directory, then get values for fixed xs as linear interpolation and put it into separate files
def extract_S_data_and_interpolate(start_point, step, scale_factor, xtype):
    if not os.path.exists(rootdir+'/'+interpolation):
        os.makedirs(rootdir+'/'+interpolation)
    min_final_x_value = -1
    for subdir, dirs, files in os.walk(rootdir+'/'+raw):
        for file in files:
            discrete_x = start_point
            alpha = 0
            left = None
            outfile = open(rootdir+'/'+interpolation+'/interpolation_'+file,'w')
            outfile.write("EVALUATION_COUNTER,CURRENT_BEST_FITNESS\n")
            infile = open(rootdir+'/'+raw+'/'+file,'r')
            lines = infile.readlines()
            final_x_value = -1
            for line in lines:
                if line.startswith("[S]"):
                    print("Processing next line")
                    data = line.split(';')
                    if xtype=='fitness':
                        curr_x_count = int(data[3])
                    elif xtype=='time':
                        if discrete_x<0:
                            alpha = int(data[1])
                            discrete_x = 0
                        curr_x_count = int(data[1])-alpha
                    else:
                        print('keeefir')
                        curr_x_count = 'kefir'
                    curr_fitness_value = data[2]
                    if curr_x_count==discrete_x:
                        resultline = str(curr_x_count//scale_factor)+','+curr_fitness_value+'\n'
                        final_x_value = curr_x_count//scale_factor
                        outfile.write(resultline)
                        discrete_x += step
                    elif curr_x_count>discrete_x:
                        while curr_x_count>discrete_x:
                            x0=left[0]
                            y0=left[1]
                            x1=curr_x_count
                            y1=float(curr_fitness_value)
                            interpolated_fitness_value = y0+(y0-y1)*(discrete_x-x0)/(x0-x1)
                            resultline = str(discrete_x//scale_factor)+','+str(interpolated_fitness_value)+'\n'
                            final_x_value = discrete_x//scale_factor
                            outfile.write(resultline)
                            discrete_x += step
                    left = (curr_x_count,float(curr_fitness_value))
            infile.close()
            outfile.close()
            if min_final_x_value==-1 or final_x_value<min_final_x_value:
                min_final_x_value = final_x_value
    return min_final_x_value


# accepts the files created by extract_S_data_and_interpolate(), and transformes values into min, max and average for each x
def map_to_min_max_avg(x_limit):
    outdata_raw = {}
    for subdir, dirs, files in os.walk(rootdir+'/'+interpolation):
        for file in files:
            infile = open(rootdir+'/'+interpolation+'/'+file,'r')
            lines = infile.readlines()
            for line in lines:
                if not line.startswith("E"):
                    data = line.split(',')
                    if not data[0] in outdata_raw:
                        outdata_raw[data[0]] = []
                    outdata_raw[data[0]].append(float(data[1]))
            infile.close()
    outdata_processed = {}
    for key in outdata_raw:
        values = outdata_raw[key]
        outdata_processed[key] = (min(values), sum(values)/len(values), max(values))
    outfile = open(rootdir+'/interpolated_minmaxavg.csv','w')
    outfile.write("EVALUATION_COUNTER,CURRENT_MIN,CURRENT_AVG,CURRENT_MAX\n")
    outdata_sorted = OrderedDict(sorted(outdata_processed.items(), key=lambda t: int(t[0])))
    for key in outdata_sorted:
        if int(key)<=x_limit:
            values = outdata_sorted[key]
            resultline = key+','+str(values[0])+','+str(values[1])+','+str(values[2])+"\n"
            outfile.write(resultline)
    outfile.close()

def main():
    # extract_S_data()
    # extract_S_data_and_snap_fitness()
    # map_to_avg()
    # extract_S_data_and_interpolate(200*factor,100*factor,factor,'fitness')
    # x_limit = extract_S_data_and_interpolate(700,100,1,'fitness')
    x_limit = extract_S_data_and_interpolate(-1,1000000000,1,'time')
    map_to_min_max_avg(x_limit)

main()
