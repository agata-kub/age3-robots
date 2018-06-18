import os
from collections import OrderedDict

rootdir ='C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-06-14/sample_1-2-25-2'
# rootdir ='C:/Agata/agh/mgr/test'
raw = 'raw'
processed = 'processed'
interpolation = 'interpolation'

# extract all data with [S] tag, and put it into separate .csv files
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

# extract all data with [S] tag from all files in raw directory, then get values afor fixed xs as linear interpolation and put it into separate files
def extract_S_data_and_interpolate():
    if not os.path.exists(rootdir+'/'+interpolation):
        os.makedirs(rootdir+'/'+interpolation)
    for subdir, dirs, files in os.walk(rootdir+'/'+raw):
        for file in files:
            step = 100
            discrete_fitness = 700
            left = None
            outfile = open(rootdir+'/'+interpolation+'/interpolation_'+file,'w')
            outfile.write("EVALUATION_COUNTER,CURRENT_BEST_FITNESS\n")
            infile = open(rootdir+'/'+raw+'/'+file,'r')
            lines = infile.readlines()
            for line in lines:
                if line.startswith("[S]"):
                    data = line.split(';')
                    curr_fitness_count = int(data[3])
                    curr_fitness_value = data[2]
                    print("Curr count "+str(curr_fitness_count)+", discrete "+str(discrete_fitness))
                    if curr_fitness_count==discrete_fitness:
                        resultline = str(curr_fitness_count)+','+curr_fitness_value+'\n'
                        outfile.write(resultline)
                        discrete_fitness += step
                    elif curr_fitness_count>discrete_fitness:
                        while curr_fitness_count>discrete_fitness:
                            x0=left[0]
                            y0=left[1]
                            x1=curr_fitness_count
                            y1=float(curr_fitness_value)
                            if x0==x1:
                                print(line)
                                print(curr_fitness_count)
                                print(discrete_fitness)
                            interpolated_fitness_value = y0+(y0-y1)*(discrete_fitness-x0)/(x0-x1)
                            resultline = str(discrete_fitness)+','+str(interpolated_fitness_value)+'\n'
                            outfile.write(resultline)
                            discrete_fitness += step
                    left = (curr_fitness_count,float(curr_fitness_value))
            infile.close()
            outfile.close()

# accepts the file created by extract_S_data_and_snap_fitness(), and transformes duplicate entries into average value
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

# accepts the files created by extract_S_data_and_interpolate(), and transformes values into min, max and average for each x
def map_to_min_max_avg():
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
        values = outdata_sorted[key]
        resultline = key+','+str(values[0])+','+str(values[1])+','+str(values[2])+"\n"
        outfile.write(resultline)
    outfile.close()


# extract best solution's fitness from all files and put them into one .csv file
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
    
# extract_S_data()
# extract_S_data_and_snap_fitness()
# map_to_avg()
extract_S_data_and_interpolate()
map_to_min_max_avg()