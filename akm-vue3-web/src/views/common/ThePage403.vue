<template>
  <div class="error-page">
    <div class="error-container">
      <div class="shield-icon">
        <div class="shield">
          <div class="shield-cross"></div>
        </div>
      </div>
      <div class="error-code">403</div>
      <h2 class="error-title">访问被拒绝</h2>
      <p class="error-desc">抱歉，您没有权限访问此页面</p>
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
      <div class="hex hex-1"></div>
      <div class="hex hex-2"></div>
      <div class="hex hex-3"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ThePage403',
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
$warning-color: #e6a23c;
$warning-light: #ebb563;
$warning-dark: #cf9236;

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
      radial-gradient(circle at 30% 70%, rgba(230, 162, 60, 0.08) 0%, transparent 50%),
      radial-gradient(circle at 70% 30%, rgba(230, 162, 60, 0.06) 0%, transparent 50%);
  }
}

.error-container {
  text-align: center;
  z-index: 10;
  animation: fadeInUp 0.8s ease-out;
}

.shield-icon {
  margin: 0 auto 24px;
  animation: pulse 2s ease-in-out infinite;

  .shield {
    width: 80px;
    height: 90px;
    margin: 0 auto;
    background: $warning-color;
    clip-path: polygon(50% 0%, 100% 25%, 100% 75%, 50% 100%, 0% 75%, 0% 25%);
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    box-shadow: 0 8px 30px rgba(230, 162, 60, 0.3);

    .shield-cross {
      position: relative;
      width: 28px;
      height: 28px;

      &::before,
      &::after {
        content: '';
        position: absolute;
        background: #fff;
        border-radius: 2px;
      }

      &::before {
        width: 4px;
        height: 28px;
        left: 50%;
        transform: translateX(-50%) rotate(45deg);
      }

      &::after {
        width: 28px;
        height: 4px;
        top: 50%;
        transform: translateY(-50%) rotate(45deg);
      }
    }
  }
}

.error-code {
  font-size: 120px;
  font-weight: 700;
  color: $warning-color;
  line-height: 1;
  margin-bottom: 16px;
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
      background: #409eff;
      border-color: #409eff;

      &:hover {
        background: #66b1ff;
        border-color: #66b1ff;
        transform: translateY(-2px);
        box-shadow: 0 6px 20px rgba(64, 158, 255, 0.35);
      }
    }

    &:not(.el-button--primary) {
      background: #fff;
      color: #606266;
      border: 1px solid #dcdfe6;

      &:hover {
        color: #409eff;
        border-color: #66b1ff;
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

  .hex {
    position: absolute;
    width: 100px;
    height: 115px;
    clip-path: polygon(50% 0%, 100% 25%, 100% 75%, 50% 100%, 0% 75%, 0% 25%);
    background: rgba(230, 162, 60, 0.06);
    border: 2px solid rgba(230, 162, 60, 0.15);
  }

  .hex-1 {
    width: 150px;
    height: 173px;
    top: 10%;
    right: 15%;
    animation: float 8s ease-in-out infinite;
  }

  .hex-2 {
    width: 80px;
    height: 92px;
    bottom: 20%;
    left: 10%;
    animation: float 6s ease-in-out infinite reverse;
  }

  .hex-3 {
    width: 120px;
    height: 138px;
    top: 60%;
    right: 8%;
    animation: float 7s ease-in-out infinite 1s;
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

@keyframes pulse {
  0%,
  100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
}

@keyframes float {
  0%,
  100% {
    transform: translateY(0) rotate(0deg);
  }
  50% {
    transform: translateY(-15px) rotate(3deg);
  }
}
</style>
