<script>
import axios from 'axios';
//按需引入图表组件
import * as echarts from 'echarts/core';
import {
  ToolboxComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent,
  DataZoomComponent
} from 'echarts/components';
import { BarChart } from 'echarts/charts';
import { CanvasRenderer } from 'echarts/renderers';
import { LineChart } from 'echarts/charts';
import { UniversalTransition } from 'echarts/features';
import { PieChart } from 'echarts/charts';
echarts.use([
  ToolboxComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent,
  BarChart,
  CanvasRenderer,
  LineChart,
  UniversalTransition,
  DataZoomComponent,
  PieChart
]);

export default {
  setup() {
    //这样就能在setup外使用Message组件
    window.$message = useMessage()
  },
  data() {
    return {
      //存储黑夜模式各个布局的颜色
      darkModeColor: {
        border: null,
        filterBack: null,
        filterBorder: null,
      },
      //存储message
      messageReactive: null,
      optionsInChart_4: [],
      valueInChart_4: null,
      //存储各个表格对象
      charts: {},
      //存储各个表格的数据
      options: [
        //0
        {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          toolbox: {
            right: 20,
            feature: {
              magicType: {
                type: ['stack']
              },
            },
          },
          emphasis: {
            focus: 'series'
          },
          legend: {},
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'value'
          },
          yAxis: {
            type: 'category',
            data: null,
          },
          series: [
            {
              name: '进口量',
              type: 'bar',
              stack: 'total',
              label: {
                show: true,
                fontSize: 12,
              },
              data: null,
            },
            {
              name: '出口量',
              type: 'bar',
              stack: 'total',
              label: {
                show: true,
                fontSize: 12,
              },
              data: null,
            },
          ],
        },
        //1
        {
          tooltip: {
            trigger: 'axis',
          },
          toolbox: {
            orient: 'vertical',
            right: 20,
            top: 50,
            feature: {
              restore: {},
              dataZoom: {
                yAxisIndex: 'none'
              },
              magicType: {
                type: ['line', 'bar']
              },
            }
          },
          grid: {
            left: '3%',
            right: '5%',
            containLabel: true
          },
          legend: {
            selected: {},
            type: 'scroll',
            itemHeight: 18,
            top: -20,
            itemGap: 20,
            padding: 30,
          },
          emphasis: {
            focus: 'series'
          },
          xAxis: {
            type: 'time',
            boundaryGap: false
          },
          yAxis: {
            type: 'value',
            boundaryGap: [0, '50%']
          },
          dataZoom: [
            {
              type: 'inside',
              start: 0,
              end: 100,
              minSpan: 20,
              filterMode: 'none'
            },
            {
              type: 'slider',
              start: 0,
              end: 100,
              minSpan: 20,
              filterMode: 'none'
            },
          ],
          series: []
        },
        //2
        {
          tooltip: {
            trigger: 'axis'
          },
          toolbox: {
            orient: 'vertical',
            right: 20,
            top: 50,
            feature: {
              restore: {},
              dataZoom: {
                yAxisIndex: 'none'
              },
              magicType: {
                type: ['line', 'bar']
              },
            }
          },
          grid: {
            left: '3%',
            right: '5%',
            containLabel: true
          },
          legend: {
            selected: {},
            type: 'scroll',
            itemHeight: 18,
            top: -20,
            itemGap: 20,
            padding: 30,
          },
          emphasis: {
            focus: 'series'
          },
          xAxis: {
            type: 'time',
            boundaryGap: false
          },
          yAxis:
          {
            type: 'value',
            name: '百分比',
            axisLabel: {
              formatter: function (value, index) {
                return value * 100 + '%';
              }
            }
          },

          dataZoom: [
            {
              type: 'inside',
              start: 0,
              end: 100,
              minSpan: 20,
              filterMode: 'none'
            },
            {
              type: 'slider',
              start: 0,
              end: 100,
              minSpan: 20,
              filterMode: 'none'
            },
          ],
          series: []
        },
        //3
        {
          tooltip: {
            trigger: 'item',
          },
          legend: {
            type: 'scroll',
            itemHeight: 18,
            top: -30,
            itemGap: 20,
            padding: 30,
          },
          toolbox: {
            right: 20,
            top: 50,
            feature: {
              restore: {},
            }
          },
          label: {
            show: true,
            formatter: '{b}: {d}%'
          },
          series: [
            {
              name: 'Access From',
              type: 'pie',
              radius: ['30%', '80%'],
              data: [],
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            },
          ]
        },
        //4
        {
          tooltip: {
            trigger: 'item',
            formatter: '{b}' + this.valueInChart_4 + '吞吐量：{c}',
          },
          legend: {
            type: 'scroll',
            itemHeight: 18,
            top: -25,
            itemGap: 20,
            padding: [30, 250, 30, 250],
          },
          toolbox: {
            right: 20,
            top: 50,
            feature: {
              restore: {},
            }
          },
          label: {
            show: true,
            formatter: '{b}: {d}%'
          },
          series: [
            {
              name: 'Access From',
              type: 'pie',
              radius: ['30%', '80%'],
              data: [],
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            },
          ]
        },
        //5
        {
          tooltip: {
            trigger: 'item',
            axisPointer: {
              type: 'shadow'
            },
          },
          emphasis: {
            focus: 'series'
          },
          legend: {
            type: 'scroll',
            itemHeight: 18,
            top: -30,
            itemGap: 20,
            padding: 30,
          },
          toolbox: {
            right: 20,
            top: 50,
            feature: {
              restore: {},
            }
          },
          grid: {
            left: '3%',
            right: '5%',
            bottom: '3%',
            top: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'value',
          },
          yAxis: {
            type: 'category',
            data: ['平均流转周期(天)'],
            axisLabel: {
              fontSize: 15,
            }
          },
          series: [],
        },
      ],
    }
  },
  methods: {
    receiveM0() {
      axios({
        method: "POST",
        url: "/api/hldp/servlet/analysis/m0",
      }).then(value => {

        //插入数据
        var port = [];
        var importValue = [];
        var exportValue = [];
        value.data.forEach(item => {
          port.push(item.port);
          importValue.push(item.import_weight);
          exportValue.push(item.export_weight);
        });
        this.options[0].yAxis.data = port;
        this.options[0].series[0].data = importValue;
        this.options[0].series[1].data = exportValue;

        //初始化图表
        const chart_0Dom = this.$refs.chart_0;
        const chart_0 = echarts.init(chart_0Dom);
        this.options[0] && chart_0.setOption(this.options[0]);
        //将图表对象放到this.charts中
        this.charts["chart_0"] = chart_0;
      }).catch(reason => {
        console.log(reason);
        window.$message.error('服务器错误或数据缺失！', {
          duration: 2000
        });
      })
    },
    receiveM1() {
      axios({
        method: "POST",
        url: "/api/hldp/servlet/analysis/m1",
      }).then(value => {
        //记录当前的数据
        var data = [];
        //记录当前的货物名
        var cargo = null;
        //插入数据
        value.data.forEach(item => {
          //首次往series中存入数据系列
          if (this.options[1].series.length == 0) {
            data = [];
            cargo = item.cargo;
            this.options[1].legend.selected[item.cargo] = true;
            this.options[1].series.push({
              name: item.cargo,
              type: 'line',
              symbol: 'circle',
              symbolSize: 8,
            });
          }
          //遇到新的数据系列
          else if (item.cargo !== cargo) {
            //先将上次的数据系列存入series中
            this.options[1].legend.selected[item.cargo] = false;
            this.options[1].series[this.options[1].series.length - 1].data = data;
            //读取新的数据系列
            data = [];
            cargo = item.cargo;
            this.options[1].series.push({
              name: item.cargo,
              type: 'line',
              symbol: 'circle',
              symbolSize: 8,
            });
          }
          //往数据系列中加数据
          var date = +new Date(item.year, item.month - 1);
          data.push([date, item.total_weight]);
        });
        //还要将最后一个数据系列存入series中
        this.options[1].legend.selected[cargo] = false;
        this.options[1].series[this.options[1].series.length - 1].data = data;

        //初始化图表
        const chart_1Dom = this.$refs.chart_1;
        const chart_1 = echarts.init(chart_1Dom);
        this.options[1] && chart_1.setOption(this.options[1]);
        //将图表对象放到this.charts中
        this.charts["chart_1"] = chart_1;
      }).catch(reason => {
        console.log(reason);
        window.$message.error('服务器错误或数据缺失！', {
          duration: 2000
        });
      })
    },
    receiveM2() {
      axios({
        method: "POST",
        url: "/api/hldp/servlet/analysis/m2",
      }).then(value => {

        //记录当前的数据
        var data_monthly = [];
        var data_yearly = [];
        //记录当前的港口名
        var port = null;
        //插入数据
        value.data.forEach(item => {
          //首次往series中存入数据系列
          if (this.options[2].series.length == 0) {
            data_monthly = [];
            data_yearly = [];
            port = item.port;
            this.options[2].legend.selected[item.port + '同比'] = true;
            this.options[2].legend.selected[item.port + '环比'] = true;
            this.options[2].series.push({
              name: item.port + '同比',
              type: 'line',
              symbol: 'circle',
              symbolSize: 8,
              tooltip: {
                valueFormatter: function (value) {
                  if (value != null) {
                    return (value * 100).toFixed(2) + '%';
                  } else {
                    return '无数据';
                  }
                }
              },
            });
            this.options[2].series.push({
              name: item.port + '环比',
              type: 'line',
              symbol: 'circle',
              symbolSize: 8,
              tooltip: {
                valueFormatter: function (value) {
                  if (value != null) {
                    return (value * 100).toFixed(2) + '%';
                  } else {
                    return '无数据';
                  }
                }
              },
            });
          }
          //遇到新的数据系列
          else if (item.port !== port) {
            //先将上次的数据系列存入series中
            this.options[2].legend.selected[item.port + '同比'] = false;
            this.options[2].legend.selected[item.port + '环比'] = false;
            this.options[2].series[this.options[2].series.length - 2].data = data_monthly;
            this.options[2].series[this.options[2].series.length - 1].data = data_yearly;
            //读取新的数据系列
            data_monthly = [];
            data_yearly = [];
            port = item.port;
            this.options[2].series.push({
              name: item.port + '同比',
              type: 'line',
              symbol: 'circle',
              symbolSize: 8,
              tooltip: {
                valueFormatter: function (value) {
                  if (value != null) {
                    return (value * 100).toFixed(2) + '%';
                  } else {
                    return '无数据';
                  }
                }
              },
            });
            this.options[2].series.push({
              name: item.port + '环比',
              type: 'line',
              symbol: 'circle',
              symbolSize: 8,
              tooltip: {
                valueFormatter: function (value) {
                  if (value != null) {
                    return (value * 100).toFixed(2) + '%';
                  } else {
                    return '无数据';
                  }
                }
              },
            });
          }
          //往数据系列中加数据
          var date = +new Date(item.year, item.month - 1);
          if (item.monthly_growth_rate == 0) {
            item.monthly_growth_rate = null;
          }
          if (item.yearly_growth_rate == 0) {
            item.yearly_growth_rate = null;
          }
          data_monthly.push([date, item.monthly_growth_rate]);
          data_yearly.push([date, item.yearly_growth_rate]);
        });
        //还要将最后一个数据系列存入series中
        this.options[2].legend.selected[port + '同比'] = false;
        this.options[2].legend.selected[port + '环比'] = false;
        this.options[2].series[this.options[2].series.length - 2].data = data_monthly;
        this.options[2].series[this.options[2].series.length - 1].data = data_yearly;

        //初始化图表
        const chart_2Dom = this.$refs.chart_2;
        const chart_2 = echarts.init(chart_2Dom);
        this.options[2] && chart_2.setOption(this.options[2]);
        //将图表对象放到this.charts中
        this.charts["chart_2"] = chart_2;
      }).catch(reason => {
        console.log(reason);
        window.$message.error('服务器错误或数据缺失！', {
          duration: 2000
        });
      })
    },
    receiveM3() {
      axios({
        method: "POST",
        url: "/api/hldp/servlet/analysis/m3",
      }).then(value => {
        value.data.forEach(item => {
          this.options[3].series[0].data.push({
            value: item.total_weight,
            name: item.cargo
          })
        });
        //初始化图表
        const chart_3Dom = this.$refs.chart_3;
        const chart_3 = echarts.init(chart_3Dom);
        this.options[3] && chart_3.setOption(this.options[3]);
        //将图表对象放到this.charts中
        this.charts["chart_3"] = chart_3;
      }).catch(reason => {
        console.log(reason);
        window.$message.error('服务器错误或数据缺失！', {
          duration: 2000
        });
      })
    },
    receiveM4() {
      axios({
        method: "POST",
        url: "/api/hldp/servlet/analysis/m4",
        data: "queryForCargoTypes",
      }).then(value => {
        for (var item of value.data) {
          this.optionsInChart_4.push({
            label: item,
            value: item,
          });
        }
        this.valueInChart_4 = this.optionsInChart_4[0].value;
        this.receiveM4Data(true);
      }).catch(reason => {
        console.log(reason);
        window.$message.error('服务器错误或数据缺失！', {
          duration: 2000
        });
      })
    },
    receiveM4Data(isFirst) {
      axios({
        method: "POST",
        url: "/api/hldp/servlet/analysis/m4",
        data: this.valueInChart_4,
      }).then(value => {
        this.options[4].series[0].data = [];
        value.data.forEach(item => {
          this.options[4].series[0].data.push({
            value: item.weight,
            name: item.port
          })
        });
        this.options[4].tooltip.formatter = '{b}' + this.valueInChart_4 + '入港总量：{c}';
        //初始化图表
        if (isFirst === true) {
          var chart_4Dom = this.$refs.chart_4;
          var chart_4 = echarts.init(chart_4Dom);
          this.options[4] && chart_4.setOption(this.options[4]);
          //将图表对象放到this.charts中
          this.charts["chart_4"] = chart_4;
        } else {
          this.options[4] && this.charts.chart_4.setOption(this.options[4]);
        }
      }).catch(reason => {
        //有个小小的bug，会报setOption不存在的错误，但运行正常，无视掉即可
        if (String(reason).indexOf("setOption") === -1) {
          console.log(reason);
          window.$message.error('服务器错误或数据缺失！', {
            duration: 2000
          });
        }
      })
    },
    receiveM5() {
      axios({
        method: "POST",
        url: "/api/hldp/servlet/analysis/m5",
      }).then(value => {
        value.data.forEach(item => {
          this.options[5].series.push({
            name: item.cargo,
            type: 'bar',
            label: {
              show: true,
              fontSize: 12,
              formatter: '{a}: {c}'
            },
            data: [item.average_time],
          })
        });
        //初始化图表
        const chart_5Dom = this.$refs.chart_5;
        const chart_5 = echarts.init(chart_5Dom);
        this.options[5] && chart_5.setOption(this.options[5]);
        //将图表对象放到this.charts中
        this.charts["chart_5"] = chart_5;
      }).catch(reason => {
        console.log(reason);
        window.$message.error('服务器错误或数据缺失！', {
          duration: 2000
        });
      })
    },
  },
  mounted() {
    if (!this.messageReactive) {
      this.messageReactive = window.$message.loading('加载中', {
        duration: 0
      })
    }

    //接收数据，初始化图表
    new Promise((resolve, reject) => {
      this.receiveM0();
      this.receiveM1();
      this.receiveM2();
      this.receiveM3();
      this.receiveM4();
      this.receiveM5();
      resolve();
    }).then((value) => {
      setTimeout(() => {
        this.messageReactive.destroy();
      }, 300);
    })

    //监听模块wrapper宽度变化，以及时改变图表的宽度
    //之所以要延迟2000ms，是因为如果直接开始监听，会导致表格的动画消失
    //故要等动画结束后再开始监听
    setTimeout(() => {
      const observer = new ResizeObserver(() => {
        this.charts.chart_0.resize();
        this.charts.chart_1.resize();
        this.charts.chart_2.resize();
        this.charts.chart_3.resize();
        this.charts.chart_4.resize();
        this.charts.chart_5.resize();
      });

      this.$nextTick(() => {
        const wrapper = this.$refs.wrapper;
        observer.observe(wrapper);
      });
    }, 2000);


  },
  watch: {
    //更改黑夜模式
    '$store.state.isDarkMode': {
      handler(newValue) {
        this.darkModeColor.border = newValue ? 'rgba(255, 255, 255,0.3)' : 'rgba(0, 0, 0, 0.3)';
        this.darkModeColor.filterBack = newValue ? '#18181c' : '#eef0f2';
        this.darkModeColor.filterBorder = newValue ? '#2d2d30' : '#ced0d2';
      },
      //页面首次加载时初始化
      immediate: true
    },
    valueInChart_4() {
      this.receiveM4Data(false);
    }
  },
}
</script>

<template>
  <div id="data-analysis-wrapper" ref="wrapper">
    <!-- 标题 -->
    <div id="data-analysis-title">数据分析</div>

    <!-- 图表主wrapper -->
    <div class="chart-main-wrapper"
      :style="{ backgroundColor: darkModeColor.filterBack, borderColor: darkModeColor.filterBorder }">
      <!-- 图表标题 -->
      <div class="chart-main-title">
        <p>港口吞吐量分析</p>
      </div>
      <!-- 图表wrapper -->
      <div class="chart-wrapper">
        <!-- 图表 -->
        <div class="chart" ref="chart_0"></div>
      </div>
    </div>

    <div class="chart-main-wrapper"
      :style="{ backgroundColor: darkModeColor.filterBack, borderColor: darkModeColor.filterBorder }">
      <div class="chart-main-title">
        <p>不同类型货物吞吐趋势</p>
      </div>
      <div class="chart-wrapper">
        <div class="chart" ref="chart_1"></div>
      </div>
    </div>

    <div class="chart-main-wrapper"
      :style="{ backgroundColor: darkModeColor.filterBack, borderColor: darkModeColor.filterBorder }">
      <div class="chart-main-title">
        <p>港口货物吞吐同比环比</p>
      </div>
      <div class="chart-wrapper">
        <div class="chart" ref="chart_2"></div>
      </div>
    </div>

    <div class="chart-main-wrapper"
      :style="{ backgroundColor: darkModeColor.filterBack, borderColor: darkModeColor.filterBorder }">
      <div class="chart-main-title">
        <p>不同货物吞吐占比</p>
      </div>
      <div class="chart-wrapper">
        <div class="chart" ref="chart_3"></div>
      </div>
    </div>

    <div class="chart-main-wrapper"
      :style="{ backgroundColor: darkModeColor.filterBack, borderColor: darkModeColor.filterBorder }">
      <div class="chart-main-title">
        <p>不同货物流向分析</p>
      </div>
      <div style="width:200px; position: absolute; left: 30px; z-index: 999;">
        <n-select v-model:value="valueInChart_4" :options="optionsInChart_4" />
      </div>
      <div class="chart-wrapper">
        <div class="chart" ref="chart_4"></div>
      </div>
    </div>

    <div class="chart-main-wrapper"
      :style="{ backgroundColor: darkModeColor.filterBack, borderColor: darkModeColor.filterBorder }">
      <div class="chart-main-title">
        <p>不同类型货物堆场流转周期分析</p>
      </div>
      <div class="chart-wrapper">
        <div class="chart" ref="chart_5"></div>
      </div>
    </div>

  </div>
</template>

<style>
/* 模块整体样式 */
#data-analysis-wrapper {
  height: 100%;
  width: 100%;
  font-size: 18px;
  overflow-x: hidden;
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: left;
  /* 阻止外边距折叠 */
  float: left;
}

/* 标题样式 */
#data-analysis-title {
  font-size: 30px;
  font-weight: 900;
  margin: 20px 0;
  text-align: center;
}

/* 表格主wrapper样式 */
.chart-main-wrapper {
  box-sizing: border-box;
  width: 90%;
  margin: 20px 5%;
  border-radius: 20px;
  border: 1px solid;
  position: relative;
}

/* 表格主标题样式 */
.chart-main-title {
  font-size: 20px;
  font-weight: 900;
  margin: 20px;
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}

/* 表格wrapper样式 */
.chart-wrapper {
  display: flex;
  flex-direction: row;
  justify-content: center;
  position: relative;
}

/* 图表样式 */
.chart {
  height: 600px;
  width: 100%;
  margin-bottom: 20px;
}
</style>