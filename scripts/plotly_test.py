import plotly.offline as offline
import plotly.plotly as py
import plotly.graph_objs as go

inpath ='C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-05-21/5-50-2/snapped_avg.csv'
filename='sample_1'
rtc=5
pop=50
rbc=2

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
        "data": [trace],
        "layout": layout
    },
    image='png'
)