import os
import sys
import json

rootdir ='C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/logs'
raw = rootdir+'/raw'
processed = rootdir+'/processed'

# extract all data with X tag, and put it into outfile
def extract_tag_data(inpath, tag):
    infile = open(inpath,'r')
    outfile = open(processed+'/'+get_filename(inpath)+'_'+tag+'.csv','w')
    lines = infile.readlines()
    for line in lines:
        if line.startswith("["+tag):
            data = line.split(';')
            resultline = ",".join(data[1:])
            outfile.write(resultline)
    infile.close()
    outfile.close()

def get_filename(filepath):
    return os.path.splitext(os.path.basename(filepath))[0]
    
def extract_solutions(inpath):
    infile = open(inpath,'r')
    outfile = open(processed+'/'+get_filename(inpath)+'_solution.json','w')
    lines = infile.readlines()
    solutions = []
    for line in lines:
        if line.startswith("[B]"):
            data = line.split(';')
            solutions.append(json.loads(data[1]))
    outfile.write(json.dumps(solutions))
    infile.close()
    outfile.close()
    

def extract_all_tags_data(inpath):
    # extract_tag_data(inpath, 'P')
    # extract_tag_data(inpath, 'W')
    extract_tag_data(inpath, 'S')
    # extract_tag_data(inpath, 'B')
    
def join_files_horizontally(indir):
    outfile = open(indir+'/aggregated.csv', 'w')
    for subdir, dirs, files in os.walk(indir):
        for file in files:
            inpath = indir+'/'+file
    outfile.close()
            

def main():
    if not os.path.exists(raw):
        os.makedirs(raw)
    if not os.path.exists(processed):
        os.makedirs(processed)
    for subdir, dirs, files in os.walk(raw):
        for file in files:
            inpath = raw+'/'+file
            extract_all_tags_data(inpath)
    # extract_solutions(inpath)
    
if __name__ == "__main__":
    main()