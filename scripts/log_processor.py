import os

rootdir ='five_rooms_two_robots_2'
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
    
generate_for_boxplot()