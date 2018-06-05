import os

rootdir ='C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-05-21/5-50-2'
raw = 'raw'
processed = 'processed'

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
extract_S_data_and_snap_fitness()
map_to_avg()