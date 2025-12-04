#!/bin/bash

# Spring Cloud Config 配置刷新测试脚本
# 使用方法: ./test-config-refresh.sh

BASE_URL="http://localhost:33000"
COLOR_GREEN='\033[0;32m'
COLOR_BLUE='\033[0;34m'
COLOR_YELLOW='\033[1;33m'
COLOR_RED='\033[0;31m'
COLOR_RESET='\033[0m'

echo -e "${COLOR_BLUE}========================================${COLOR_RESET}"
echo -e "${COLOR_BLUE}Spring Cloud Config 配置刷新测试${COLOR_RESET}"
echo -e "${COLOR_BLUE}========================================${COLOR_RESET}"
echo ""

# 1. 查看配置来源
echo -e "${COLOR_YELLOW}1. 查看配置来源信息${COLOR_RESET}"
echo "GET ${BASE_URL}/demo/config/open/info"
curl -s "${BASE_URL}/demo/config/open/info" | python3 -m json.tool
echo ""
echo ""

# 2. 查看刷新前的配置
echo -e "${COLOR_YELLOW}2. 查看刷新前的配置${COLOR_RESET}"
echo "GET ${BASE_URL}/demo/config/open/refresh-test"
curl -s "${BASE_URL}/demo/config/open/refresh-test" | python3 -m json.tool
echo ""
echo ""

# 3. 提示修改配置
echo -e "${COLOR_RED}请在 Config Server 中修改配置文件，然后按回车继续...${COLOR_RESET}"
echo -e "${COLOR_YELLOW}例如修改: akm.cacheType 或 akm.enabledAuth${COLOR_RESET}"
read -p ""

# 4. 刷新配置
echo -e "${COLOR_YELLOW}3. 触发配置刷新${COLOR_RESET}"
echo "POST ${BASE_URL}/actuator/refresh"
REFRESH_RESULT=$(curl -s -X POST "${BASE_URL}/actuator/refresh")
echo -e "${COLOR_GREEN}刷新结果:${COLOR_RESET}"
echo "${REFRESH_RESULT}" | python3 -m json.tool
echo ""
echo ""

# 5. 查看刷新后的配置
echo -e "${COLOR_YELLOW}4. 查看刷新后的配置${COLOR_RESET}"
echo "GET ${BASE_URL}/demo/config/open/refresh-test"
curl -s "${BASE_URL}/demo/config/open/refresh-test" | python3 -m json.tool
echo ""
echo ""

# 6. 触发配置日志记录
echo -e "${COLOR_YELLOW}5. 触发配置日志记录${COLOR_RESET}"
echo "POST ${BASE_URL}/demo/config/open/trigger-refresh-log"
curl -s -X POST "${BASE_URL}/demo/config/open/trigger-refresh-log" | python3 -m json.tool
echo ""
echo ""

echo -e "${COLOR_GREEN}========================================${COLOR_RESET}"
echo -e "${COLOR_GREEN}测试完成！请查看应用日志确认配置已刷新${COLOR_RESET}"
echo -e "${COLOR_GREEN}========================================${COLOR_RESET}"

