<script>
import * as echarts from 'echarts';
export default {
  data() {
    return {
      //存储黑夜模式各个布局的颜色
      darkModeColor: {
        border: null,
        filterBack: null,
        filterBorder: null,
      },
      //存储各个表格的数据
      options: [
        {
          tooltip: {
            trigger: 'axis'
          },
          legend: {},
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              name: 'Email',
              type: 'line',
              data: [120, 132, 101, 134, 90, 230, 210]
            },
            {
              name: 'Union Ads',
              type: 'line',
              data: [220, 182, 191, 234, 290, 330, 310]
            },
            {
              name: 'Video Ads',
              type: 'line',
              data: [150, 232, 201, 154, 190, 330, 410]
            },
            {
              name: 'Direct',
              type: 'line',
              data: [320, 332, 301, 334, 390, 330, 320]
            },
            {
              name: 'Search Engine',
              type: 'line',
              data: [820, 932, 901, 934, 1290, 1330, 1320]
            }
          ]
        },
        {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {},
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            data: ['Brazil', 'Indonesia', 'USA', 'India', 'China', 'World']
          },
          yAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
          },
          series: [
            {
              name: '2011',
              type: 'bar',
              data: [18203, 23489, 29034, 104970, 131744, 630230]
            },
            {
              name: '2012',
              type: 'bar',
              data: [19325, 23438, 31000, 121594, 134141, 681807]
            }
          ]
        },

      ],
      //存储文本中提及的数据
      text:[

      ]
    }
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
  },
  mounted() {
    //监听模块wrapper宽度变化，以及时改变图表的宽度
    //之所以要延迟1500ms，是因为如果直接开始监听，会导致表格的动画消失
    //故要等动画结束后再开始监听
    setTimeout(() => {
      const observer = new ResizeObserver(() => {
        chart_0.resize();
        chart_1.resize();
      });

      this.$nextTick(() => {
        const wrapper = this.$refs.wrapper;
        observer.observe(wrapper);
      });
    }, 1500);

    //接收数据...


    //初始化图表
    const chart_0Dom = this.$refs.chart_0;
    const chart_0 = echarts.init(chart_0Dom);
    this.options[0] && chart_0.setOption(this.options[0]);

    const chart_1Dom = this.$refs.chart_1;
    const chart_1 = echarts.init(chart_1Dom);
    this.options[1] && chart_1.setOption(this.options[1]);

    //每隔一定时间更新数据...


  }
}
</script>

<template>
  <div id="data-analysis-wrapper" ref="wrapper">
    <!-- 标题 -->
    <div id="company-data-title">数据分析</div>

    <!-- 图表主wrapper -->
    <div class="chart-main-wrapper"
      :style="{ backgroundColor: darkModeColor.filterBack, borderColor: darkModeColor.filterBorder }">
      <!-- 图表标题 -->
      <div class="chart-main-title">
        <p>图表1</p>
      </div>
      <!-- 图表wrapper -->
      <div class="chart-wrapper">
        <!-- 图表 -->
        <div class="chart" ref="chart_0"></div>
        <!-- 分割线 -->
        <div class="divider" :style="{ borderColor: darkModeColor.filterBorder }"></div>
        <!-- 图表文本 -->
        <div class="chart-text">
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Nesciunt temporibus nisi cupiditate dolores adipisci ea
          consequuntur repellendus cumque ducimus harum obcaecati atque libero maiores dignissimos doloribus, suscipit
          voluptas ut reiciendis.
        </div>
      </div>
    </div>

    <div class="chart-main-wrapper"
      :style="{ backgroundColor: darkModeColor.filterBack, borderColor: darkModeColor.filterBorder }">
      <div class="chart-main-title">
        <p>图表2</p>
      </div>
      <div class="chart-wrapper">
        <div class="chart" ref="chart_1"></div>
        <div class="divider" :style="{ borderColor: darkModeColor.filterBorder }"></div>
        <div class="chart-text">
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Ratione sit amet provident at accusantium. Ullam magnam
          labore neque at odio, accusamus consequatur officia ad non ea nesciunt. Beatae, obcaecati illum.
        </div>
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
  margin: 30px 5%;
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
  height: 500px;
  width: 70%;
}

/* 分割线样式 */
.divider {
  height: 500px;
  border: 1px solid;
}

/* 图表文本样式 */
.chart-text {
  height: 500px;
  width: 30%;
  margin: 10px;
  line-height: 30px;
  text-align: center;
  overflow: auto;
  user-select:text;
}
</style>