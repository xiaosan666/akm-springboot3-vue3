<template>
  <div class="error-page">
    <div class="error-container">
      <div class="error-code">
        <span class="digit">4</span>
        <span class="digit zero">
          <div class="eye"></div>
        </span>
        <span class="digit">4</span>
      </div>
      <h2 class="error-title">页面走丢了</h2>
      <p class="error-desc">抱歉，您访问的页面不存在或已被移除</p>
      <div class="error-actions">
        <el-button type="primary" size="large" @click="goHome">
          <el-icon><HomeFilled /></el-icon>
          返回首页
        </el-button>
        <el-button size="large" @click="goBack">
          <el-icon><Back /></el-icon>
          返回上页
        </el-button>
      </div>
    </div>
    <div class="decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ThePage404',
  methods: {
    goHome() {
      this.$router.push('/')
    },
    goBack() {
      this.$router.go(-1)
    },
  },
}
</script>

<style lang="scss" scoped>
$primary-color: #409eff;
$primary-light: #66b1ff;
$primary-dark: #3a8ee6;

.error-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  position: relative;
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background:
      radial-gradient(circle at 20% 80%, rgba(64, 158, 255, 0.08) 0%, transparent 50%),
      radial-gradient(circle at 80% 20%, rgba(64, 158, 255, 0.06) 0%, transparent 50%);
  }
}

.error-container {
  text-align: center;
  z-index: 10;
  animation: fadeInUp 0.8s ease-out;
}

.error-code {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-bottom: 24px;

  .digit {
    font-size: 140px;
    font-weight: 700;
    color: $primary-color;
    line-height: 1;
  }

  .zero {
    width: 120px;
    height: 120px;
    background: $primary-color;
    border-radius: 50%;
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    box-shadow: 0 8px 30px rgba(64, 158, 255, 0.3);

    .eye {
      width: 28px;
      height: 28px;
      background: #fff;
      border-radius: 50%;
      position: relative;
      animation: lookAround 3s ease-in-out infinite;

      &::after {
        content: '';
        position: absolute;
        width: 12px;
        height: 12px;
        background: #303133;
        border-radius: 50%;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
      }
    }
  }
}

.error-title {
  font-size: 28px;
  color: #303133;
  margin: 0 0 12px;
  font-weight: 600;
}

.error-desc {
  font-size: 15px;
  color: #909399;
  margin: 0 0 36px;
}

.error-actions {
  display: flex;
  gap: 16px;
  justify-content: center;

  :deep(.el-button) {
    padding: 12px 28px;
    font-size: 14px;
    border-radius: 8px;
    font-weight: 500;
    transition: all 0.3s ease;

    &.el-button--primary {
      background: $primary-color;
      border-color: $primary-color;

      &:hover {
        background: $primary-light;
        border-color: $primary-light;
        transform: translateY(-2px);
        box-shadow: 0 6px 20px rgba(64, 158, 255, 0.35);
      }
    }

    &:not(.el-button--primary) {
      background: #fff;
      color: #606266;
      border: 1px solid #dcdfe6;

      &:hover {
        color: $primary-color;
        border-color: $primary-light;
        background: #ecf5ff;
        transform: translateY(-2px);
      }
    }
  }
}

.decoration {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  pointer-events: none;

  .circle {
    position: absolute;
    border-radius: 50%;
    border: 2px solid rgba(64, 158, 255, 0.15);
    background: rgba(64, 158, 255, 0.02);
  }

  .circle-1 {
    width: 400px;
    height: 400px;
    top: -150px;
    right: -150px;
    animation: rotate 30s linear infinite;
  }

  .circle-2 {
    width: 300px;
    height: 300px;
    bottom: -100px;
    left: -100px;
    animation: rotate 25s linear infinite reverse;
  }

  .circle-3 {
    width: 200px;
    height: 200px;
    top: 40%;
    left: 8%;
    animation: float 6s ease-in-out infinite;
  }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes lookAround {
  0%,
  100% {
    transform: translateX(0);
  }
  25% {
    transform: translateX(8px);
  }
  75% {
    transform: translateX(-8px);
  }
}

@keyframes rotate {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

@keyframes float {
  0%,
  100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-20px);
  }
}
</style>
