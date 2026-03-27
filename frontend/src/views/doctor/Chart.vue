<template>
  <div>
    <h2>诊断准确率: {{ accuracy }}%</h2>
  </div>
</template>

<script>
import axios from 'axios';
import request from "@/utils/request";

export default {
  data() {
    return {
      accuracy: 0
    };
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      request.get('/user') // 确保这是获取数据的正确URL
          .then(response => {
            const data = response.data;
            console.log("Received data:", response.data.status);
            this.calculateAccuracy(data);
          })
          .catch(error => console.error("Error fetching data:", error));
    },
    calculateAccuracy(data) {
      const total = data.length;
      const consistent = data.filter(item => item.status === item.result).length;
      this.accuracy = total ? ((consistent / total) * 100).toFixed(2) : "无数据";
    }
  }
}
</script>


