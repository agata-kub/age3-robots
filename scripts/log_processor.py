import os
import sys
import json

# extract all data with X tag, and put it into outfile
def extract_tag_data(inpath, tag):
    infile = open(inpath,'r')
    outdir = os.path.dirname(inpath)+'/output/'
    outfile = open(outdir+get_filename(inpath)+'_'+tag+'.csv','w')
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
    outdir = os.path.dirname(inpath)+'/output/'
    outfile = open(outdir+get_filename(inpath)+'_solution.json','w')
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
    extract_tag_data(inpath, 'P')
    extract_tag_data(inpath, 'W')
    extract_tag_data(inpath, 'S')
    extract_tag_data(inpath, 'B')

def main():
    inpath = sys.argv[1]
    outdir = os.path.dirname(inpath)+'/output/'
    if not os.path.exists(outdir):
        os.makedirs(outdir)
#    extract_all_tags_data(inpath)
    extract_solutions(inpath)
    
if __name__ == "__main__":
    main()