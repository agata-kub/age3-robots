import plotly.offline as offline
import plotly.plotly as py
import plotly.graph_objs as go
import plotly.io as pio

# inpath_emas = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-08-31/2ndFloor-2-50-2/interpolated_minmaxavg.csv'
# inpath_ea = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-08-31/2ndFloor-2-50-2-ea/interpolated_minmaxavg.csv'

inpath_emas = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-18/sample_1-2-50-2-time/interpolated_minmaxavg.csv'
inpath_ea = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-18/sample_1-2-50-2-ea-time/interpolated_minmaxavg.csv'
inpath_bfs = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-08-27/sample_1-2-50-2/bfs.txt'

inpath_1 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-08-27/sample_1-1-50-2/interpolated_minmaxavg.csv'
inpath_2 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-08-27/sample_1-2-50-2/interpolated_minmaxavg.csv'
inpath_3 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-08-27/sample_1-3-50-2/interpolated_minmaxavg.csv'
inpath_5 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-08-27/sample_1-5-50-2/interpolated_minmaxavg.csv'

inpath_2ndFloor_2 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-18/2ndFloor-2-50-2-time/interpolated_minmaxavg.csv'
inpath_2ndFloor_4 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-18/2ndFloor-4-50-2-time/interpolated_minmaxavg.csv'
inpath_2ndFloor_6 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-18/2ndFloor-6-50-2-time/interpolated_minmaxavg.csv'
inpath_2ndFloor_8 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-18/2ndFloor-8-50-2-time/interpolated_minmaxavg.csv'
inpath_2ndFloor_10 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-18/2ndFloor-10-50-2-time/interpolated_minmaxavg.csv'
inpath_2ndFloor_12 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-18/2ndFloor-12-50-2-time/interpolated_minmaxavg.csv'
inpath_2ndFloor_14 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-18/2ndFloor-14-50-2-time/interpolated_minmaxavg.csv'

inpath_2ndFloor_25 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-19/2ndFloor-10-25-2-time/interpolated_minmaxavg.csv'
inpath_2ndFloor_50 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-18/2ndFloor-10-50-2-time/interpolated_minmaxavg.csv'
inpath_2ndFloor_100 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-19/2ndFloor-10-100-2-time/interpolated_minmaxavg.csv'
inpath_2ndFloor_150 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-19/2ndFloor-10-150-2-time/interpolated_minmaxavg.csv'
inpath_2ndFloor_200 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-19/2ndFloor-10-200-2-time/interpolated_minmaxavg.csv'

inpath_4Floors_8 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-19/4Floors-4-100-8-time/interpolated_minmaxavg.csv'
inpath_4Floors_12 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-19/4Floors-4-100-12-time/interpolated_minmaxavg.csv'
inpath_4Floors_16 = 'C:/Agata/agh/mgr/age/age3-ogr-labs/age3-robots/archive/2018-09-19/4Floors-4-100-16-time/interpolated_minmaxavg.csv'



# params = (
#     (inpath_emas, 'EMAS', 'GREEN', None),
#     (inpath_ea, 'EA', 'BLUE', inpath_bfs)
# )

# params = (
#     (inpath_2ndFloor_2, 'routes count=2', 'DARK_GREEN', None),
#     (inpath_2ndFloor_4, 'routes count=4', 'GREEN', None),
# 	(inpath_2ndFloor_6, 'routes count=6', 'BLUE', None),
# 	(inpath_2ndFloor_8, 'routes count=8', 'VIOLET', None),
# 	(inpath_2ndFloor_10, 'routes count=10', 'ORANGE', None),
# 	(inpath_2ndFloor_12, 'routes count=12', 'PINK', None),
# 	(inpath_2ndFloor_14, 'routes count=14', 'YELLOW', None)
# )

# params = (
#     (inpath_2ndFloor_25, 'population=25', 'GREEN', None),
# 	(inpath_2ndFloor_50, 'population=50', 'BLUE', None),
# 	(inpath_2ndFloor_100, 'population=100', 'VIOLET', None),
# 	(inpath_2ndFloor_150, 'population=150', 'ORANGE', None),
# 	(inpath_2ndFloor_200, 'population=200', 'PINK', None)
# )

params = (
    (inpath_4Floors_8, 'robots count=8', 'GREEN', 4.813667),
	(inpath_4Floors_12, 'robots count=12', 'BLUE', None),
    (inpath_4Floors_16, 'robots count=16', 'VIOLET', None)
)

MARKER_SIZE = 4

filename='sample_1'
rtc=2
pop=50
rbc=2

# min, avg, max, fill
plot_colors = {
    'DARK_GREEN' : ['#3c7932', '#0C5800', '#094600'],
    'BLUE' : ['#4498c1', '#167fb2', '#11658e'],
    'GREEN' : ['#80c691', '#61b876', '#4d935e'],
    'VIOLET': ['#6f3fce', '#7d52d2', '#8b65d7'],
    'ORANGE': ['#d57c3e', '#ed8a45', '#ee9557'],
    'PINK': ['#ff5a9c', '#FF3184', '#cc2769'],
    'YELLOW': ['#ffd963', '#ffd03d', '#e5bb36']
}

# CURRENTLY NOT IN USE
def get_avg_trace(inpath):
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

def get_minmaxavg_traces(inpath, label, colorname, bfsval):
    infile = open(inpath,'r')
    lines = infile.readlines()
    eval_count = []
    minf = []
    avgf = []
    maxf = []
    bfs = []
    zero = []
    # bfsval = None
    # if bfs_inpath is not None:
    #     bfsinfile = open(bfs_inpath,'r')
    #     bfsval = float(bfsinfile.readline())
    #     bfsinfile.close()
    for line in lines:
        if not line.startswith('E'):
            data = line.split(',')
            eval_count.append(int(data[0]))
            minf.append(float(data[1]))
            avgf.append(float(data[2]))
            maxf.append(float(data[3]))
            if bfsval is not None:
                bfs.append(bfsval)
                # bfs.append(7.52309)
    infile.close()
    mintrace = go.Scatter(
        x=eval_count,
        y=minf,
        mode = 'lines+markers',
        name = label+' min',
        line=dict(
            color=plot_colors[colorname][0],
        ),
        marker = dict(
            size=MARKER_SIZE
        )
    )
    avgtrace = go.Scatter(
        x=eval_count,
        y=avgf,
        # fill='tonexty',
        # fillcolor=plot_colors[prefix][3],
        mode = 'lines+markers',
        name = label+' avg',
        line = dict(
            color=plot_colors[colorname][1],
        ),
        marker = dict(
            size=MARKER_SIZE
        )
    )
    maxtrace = go.Scatter(
        x=eval_count,
        y=maxf,
        # fill='tonexty',
        # fillcolor=plot_colors[prefix][3],
        mode = 'lines+markers',
        name = label+' max',
        line=dict(
            color=plot_colors[colorname][2],
        ),
        marker = dict(
            size=MARKER_SIZE
        )
    )
    if bfsval is not None:
        bfstrace = go.Scatter(x=eval_count, y=bfs, mode = 'lines+markers', line=dict(color='#990000'), name = 'greedy')
        return [mintrace, avgtrace, maxtrace, bfstrace]
    return [mintrace, avgtrace, maxtrace]
    # return [avgtrace]

traces = []

for param in params:
    traces.extend(get_minmaxavg_traces(param[0], param[1], param[2], param[3]))
    
layout = go.Layout(
    title='4Floors - robots count',
    # title='{} - robots count {} - population {} - routes count {}'.format(filename, rtc, pop, rbc),
    xaxis=dict(
        title='time (ns)',
        # title='fitness evaluations count',
        titlefont=dict(
            size=16
        )
    ),
    yaxis=dict(
        title='current best fitness',
        titlefont=dict(
            size=16
        ),
        type='log'
        # type='linear'
    ),
    legend=dict(
        font=dict(
            size=16
        )
    )
)

# graph = offline.plot(
#     {
#         "data": traces,
#         "layout": layout
#     },
#     image='png'
# )

figure = go.Figure(traces, layout)
pio.write_image(figure, 'fig1.pdf', width=1920, height=1080)
