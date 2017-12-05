import os
import sys

# extract all data with X tag, and put it into outfile
def extract_tag_data(inpath, outpath, tag):
    infile = open(inpath,'r')
    outfile = open(outpath,'w')
    lines = infile.readlines()
    for line in lines:
        if line.startswith("["+tag):
            data = line.split(';')
            resultline = ",".join(data[1:])
            outfile.write(resultline)
    infile.close()
    outfile.close()

def main():
    inpath = sys.argv[1]
    outpath = sys.argv[2]
    tag = sys.argv[3]
    extract_tag_data(inpath, outpath, tag)
    
if __name__ == "__main__":
    main()