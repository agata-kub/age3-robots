import plotly.offline as offline
import plotly.plotly as py
import plotly.graph_objs as go

inpath ='C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-06-14/sample_1-2-25-2/interpolated_minmaxavg.csv'
filename='sample_1'
rtc=2
pop=50
rbc=2

def get_avg_trace():
    infile = open(inpath,'r')
    lines = infile.readlines()
    eval_count = []
    fitness = []
    for line in lines:
        if not line.startswith('E'):
            data = line.split(',')
            eval_count.append(int(data[0]))
            fitness.append(float(data[1]))
    infile.close()
    trace = go.Scatter(
        x=eval_count,
        y=fitness,
        mode = 'markers'
    )
    return [trace]

def get_minmaxavg_traces():
    infile = open(inpath,'r')
    lines = infile.readlines()
    eval_count = []
    minf = []
    avgf = []
    maxf = []
    # bfs = []
    for line in lines:
        if not line.startswith('E'):
            data = line.split(',')
            eval_count.append(int(data[0]))
            minf.append(float(data[1]))
            avgf.append(float(data[2]))
            maxf.append(float(data[3]))
            # bfs.append(8.7411485E5)
    infile.close()
    mintrace = go.Scatter(x=eval_count, y=minf, mode = 'lines+markers', name = 'min')
    avgtrace = go.Scatter(x=eval_count, y=avgf, mode = 'lines+markers', name = 'avg')
    maxtrace = go.Scatter(x=eval_count, y=maxf, mode = 'lines+markers', name = 'max')
    # bfstrace = go.Scatter(x=eval_count, y=bfs, mode = 'lines+markers', name = 'bfs')
    return [mintrace, avgtrace, maxtrace]



traces = get_minmaxavg_traces()
    
layout = go.Layout(
    title='{} - robots count {} - population {} - routes count {}'.format(filename, rtc, pop, rbc),
    xaxis=dict(
        title='fitness evaluations count'
    ),
    yaxis=dict(
        title='current best fitness'
    )
)

graph = offline.plot(
    {
        "data": traces,
        "layout": layout
    },
    image='png'
)