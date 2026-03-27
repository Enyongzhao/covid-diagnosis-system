<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="紧急通知" name="first">
      <el-collapse v-model="activeNames" @change="handleChange">
        <el-collapse-item title="通知" name="1">
          <div>好好学习，天天向上！</div>
        </el-collapse-item>
      </el-collapse>
    </el-tab-pane>
    <el-tab-pane label="会议通知" name="second">
      <el-collapse v-model="activeNames" @change="handleChange">
        <el-collapse-item title="今日通知" name="1">
        </el-collapse-item>
      </el-collapse>
    </el-tab-pane>
    <el-tab-pane label="活动通知" name="third">
      <el-collapse v-model="activeNames" @change="handleChange">
        <el-collapse-item title="通知" name="1">
        </el-collapse-item>
      </el-collapse>
    </el-tab-pane>
  </el-tabs>
</template>
<script>
export default {
  data() {
    return {
      activeName: 'first'
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    }
  }
};
</script>

