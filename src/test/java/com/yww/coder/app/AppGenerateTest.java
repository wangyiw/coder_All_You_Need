package com.yww.coder.app;

import com.yww.coder.common.dto.PageResponseDto;
import com.yww.coder.genapp.model.dto.AppAdminUpdateRequestDto;
import com.yww.coder.genapp.model.dto.AppDetailResponseDto;
import com.yww.coder.genapp.model.dto.AppPageQueryRequestDto;
import com.yww.coder.genapp.model.dto.GenerateAppRequestDto;
import com.yww.coder.genapp.model.dto.UpdateAppRequestDto;
import com.yww.coder.genapp.service.AppService;
import com.yww.coder.user.model.entity.User;
import com.yww.coder.user.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import static com.yww.coder.constant.UserConstant.USER_LOGIN_STATE;

/**
 * App 模块单元测试
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppGenerateTest {

    @Resource
    private AppService appService;

    @Resource
    private UserService userService;

    private static Long testUserId;
    private static Long testAppId;
    private static MockHttpServletRequest mockRequest;

    @BeforeAll
    static void setupAll() {
        System.out.println("========== 开始 App 模块单元测试 ==========");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("========== App 模块单元测试完成 ==========");
    }

    @BeforeEach
    void setup() {
        // 创建 Mock 请求
        mockRequest = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        mockRequest.setSession(session);

        // 模拟登录用户（使用数据库中已存在的用户）
        if (testUserId == null) {
            // 获取第一个普通用户
            User testUser = userService.getUserById(1L);
            if (testUser != null) {
                testUserId = testUser.getId();
                session.setAttribute(USER_LOGIN_STATE, testUser);
            }
        } else {
            User testUser = userService.getUserById(testUserId);
            session.setAttribute(USER_LOGIN_STATE, testUser);
        }
    }

    @Test
    @Order(1)
    @DisplayName("1. 【用户】创建应用")
    void testCreateApp() {
        System.out.println("\n===== 测试：创建应用 =====");
        
        GenerateAppRequestDto requestDto = new GenerateAppRequestDto();
        requestDto.setInitPrompt("创建一个个人博客应用，包含文章列表、文章详情、关于我等页面");

        Long appId = appService.createApp(requestDto, mockRequest);
        
        Assertions.assertNotNull(appId, "应用 ID 不应为空");
        Assertions.assertTrue(appId > 0, "应用 ID 应大于 0");
        
        testAppId = appId;
        System.out.println("创建成功，应用 ID: " + appId);
    }

    @Test
    @Order(2)
    @DisplayName("2. 【用户】查看应用详情")
    void testGetAppDetail() {
        System.out.println("\n===== 测试：查看应用详情 =====");
        
        Assertions.assertNotNull(testAppId, "测试应用 ID 不应为空");
        
        AppDetailResponseDto detail = appService.getAppDetail(testAppId);
        
        Assertions.assertNotNull(detail, "应用详情不应为空");
        Assertions.assertEquals(testAppId, detail.getId(), "应用 ID 应匹配");
        Assertions.assertNotNull(detail.getDeployKey(), "部署标识不应为空");
        Assertions.assertEquals(10, detail.getDeployKey().length(), "部署标识应为 10 位");
        Assertions.assertNotNull(detail.getInitPrompt(), "初始化提示词不应为空");
        
        System.out.println("应用详情: " + detail);
    }

    @Test
    @Order(3)
    @DisplayName("3. 【用户】修改自己的应用名称")
    void testUpdateMyApp() {
        System.out.println("\n===== 测试：修改应用名称 =====");
        
        Assertions.assertNotNull(testAppId, "测试应用 ID 不应为空");
        
        UpdateAppRequestDto requestDto = new UpdateAppRequestDto();
        requestDto.setId(testAppId);
        requestDto.setAppName("我的个人博客 V2.0");

        Boolean result = appService.updateMyApp(requestDto, mockRequest);
        
        Assertions.assertTrue(result, "更新应该成功");
        
        // 验证更新结果
        AppDetailResponseDto detail = appService.getAppDetail(testAppId);
        Assertions.assertEquals("我的个人博客 V2.0", detail.getAppName(), "应用名称应已更新");
        
        System.out.println("更新成功，新名称: " + detail.getAppName());
    }

    @Test
    @Order(4)
    @DisplayName("4. 【用户】分页查询自己的应用列表")
    void testListMyAppsByPage() {
        System.out.println("\n===== 测试：分页查询自己的应用 =====");
        
        AppPageQueryRequestDto queryRequest = new AppPageQueryRequestDto();
        queryRequest.setCurrent(1);
        queryRequest.setSize(10);

        PageResponseDto<AppDetailResponseDto> pageResponse = appService.listMyAppsByPage(queryRequest, mockRequest);
        
        Assertions.assertNotNull(pageResponse, "分页结果不应为空");
        Assertions.assertNotNull(pageResponse.getList(), "应用列表不应为空");
        Assertions.assertTrue(pageResponse.getTotal() > 0, "应该至少有一个应用");
        
        System.out.println("查询到 " + pageResponse.getTotal() + " 个应用");
        pageResponse.getList().forEach(app -> 
            System.out.println("  - " + app.getAppName() + " (ID: " + app.getId() + ")")
        );
    }

    @Test
    @Order(5)
    @DisplayName("5. 【用户】按名称模糊查询自己的应用")
    void testListMyAppsByName() {
        System.out.println("\n===== 测试：按名称模糊查询 =====");
        
        AppPageQueryRequestDto queryRequest = new AppPageQueryRequestDto();
        queryRequest.setCurrent(1);
        queryRequest.setSize(10);
        queryRequest.setAppName("博客");

        PageResponseDto<AppDetailResponseDto> pageResponse = appService.listMyAppsByPage(queryRequest, mockRequest);
        
        Assertions.assertNotNull(pageResponse, "分页结果不应为空");
        System.out.println("查询到 " + pageResponse.getTotal() + " 个包含'博客'的应用");
    }

    @Test
    @Order(6)
    @DisplayName("6. 【用户】分页查询精选应用列表")
    void testListFeaturedAppsByPage() {
        System.out.println("\n===== 测试：分页查询精选应用 =====");
        
        AppPageQueryRequestDto queryRequest = new AppPageQueryRequestDto();
        queryRequest.setCurrent(1);
        queryRequest.setSize(10);

        PageResponseDto<AppDetailResponseDto> pageResponse = appService.listFeaturedAppsByPage(queryRequest);
        
        Assertions.assertNotNull(pageResponse, "分页结果不应为空");
        Assertions.assertNotNull(pageResponse.getList(), "应用列表不应为空");
        
        System.out.println("查询到 " + pageResponse.getTotal() + " 个精选应用");
        pageResponse.getList().forEach(app -> 
            System.out.println("  - " + app.getAppName() + " (优先级: " + app.getPriority() + ")")
        );
    }

    @Test
    @Order(7)
    @DisplayName("7. 【管理员】分页查询所有应用")
    void testAdminListAppsByPage() {
        System.out.println("\n===== 测试：管理员分页查询所有应用 =====");
        
        AppPageQueryRequestDto queryRequest = new AppPageQueryRequestDto();
        queryRequest.setCurrent(1);
        queryRequest.setSize(20);

        PageResponseDto<AppDetailResponseDto> pageResponse = appService.adminListAppsByPage(queryRequest);
        
        Assertions.assertNotNull(pageResponse, "分页结果不应为空");
        Assertions.assertNotNull(pageResponse.getList(), "应用列表不应为空");
        
        System.out.println("查询到 " + pageResponse.getTotal() + " 个应用");
    }

    @Test
    @Order(8)
    @DisplayName("8. 【管理员】更新任意应用")
    void testAdminUpdateApp() {
        System.out.println("\n===== 测试：管理员更新应用 =====");
        
        Assertions.assertNotNull(testAppId, "测试应用 ID 不应为空");
        
        AppAdminUpdateRequestDto requestDto = new AppAdminUpdateRequestDto();
        requestDto.setId(testAppId);
        requestDto.setAppName("【精选】个人博客应用");
        requestDto.setCover("https://example.com/cover.jpg");
        requestDto.setPriority(99);

        Boolean result = appService.adminUpdateApp(requestDto);
        
        Assertions.assertTrue(result, "更新应该成功");
        
        // 验证更新结果
        AppDetailResponseDto detail = appService.getAppDetail(testAppId);
        Assertions.assertEquals("【精选】个人博客应用", detail.getAppName(), "应用名称应已更新");
        Assertions.assertEquals(99, detail.getPriority(), "优先级应已更新");
        
        System.out.println("更新成功，新名称: " + detail.getAppName() + ", 优先级: " + detail.getPriority());
    }

    @Test
    @Order(9)
    @DisplayName("9. 【管理员】按条件查询应用")
    void testAdminListAppsByCondition() {
        System.out.println("\n===== 测试：管理员按条件查询应用 =====");
        
        AppPageQueryRequestDto queryRequest = new AppPageQueryRequestDto();
        queryRequest.setCurrent(1);
        queryRequest.setSize(20);
        queryRequest.setPriority(99);

        PageResponseDto<AppDetailResponseDto> pageResponse = appService.adminListAppsByPage(queryRequest);
        
        Assertions.assertNotNull(pageResponse, "分页结果不应为空");
        System.out.println("查询到 " + pageResponse.getTotal() + " 个优先级为 99 的应用");
    }

    @Test
    @Order(10)
    @DisplayName("10. 【用户】删除自己的应用")
    void testDeleteMyApp() {
        System.out.println("\n===== 测试：删除自己的应用 =====");
        
        Assertions.assertNotNull(testAppId, "测试应用 ID 不应为空");
        
        Boolean result = appService.deleteMyApp(testAppId, mockRequest);
        
        Assertions.assertTrue(result, "删除应该成功");
        
        // 验证删除结果（应该抛出异常，因为应用已被软删除）
        Assertions.assertThrows(Exception.class, () -> {
            appService.getAppDetail(testAppId);
        }, "查询已删除的应用应该抛出异常");
        
        System.out.println("删除成功，应用 ID: " + testAppId);
    }

    @Test
    @Order(11)
    @DisplayName("11. 创建多个测试应用用于分页测试")
    void testCreateMultipleApps() {
        System.out.println("\n===== 测试：批量创建应用 =====");
        
        String[] prompts = {
            "创建一个在线商城应用",
            "创建一个任务管理系统",
            "创建一个图片分享平台",
            "创建一个音乐播放器",
            "创建一个天气预报应用"
        };

        for (String prompt : prompts) {
            GenerateAppRequestDto requestDto = new GenerateAppRequestDto();
            requestDto.setInitPrompt(prompt);
            
            Long appId = appService.createApp(requestDto, mockRequest);
            Assertions.assertNotNull(appId, "应用创建应该成功");
            System.out.println("创建应用: " + prompt + " (ID: " + appId + ")");
        }
        
        System.out.println("批量创建完成");
    }

    @Test
    @Order(12)
    @DisplayName("12. 测试分页功能")
    void testPagination() {
        System.out.println("\n===== 测试：分页功能 =====");
        
        // 第一页
        AppPageQueryRequestDto queryRequest1 = new AppPageQueryRequestDto();
        queryRequest1.setCurrent(1);
        queryRequest1.setSize(3);
        
        PageResponseDto<AppDetailResponseDto> page1 = appService.listMyAppsByPage(queryRequest1, mockRequest);
        System.out.println("第 1 页: " + page1.getList().size() + " 条记录");
        
        // 第二页
        AppPageQueryRequestDto queryRequest2 = new AppPageQueryRequestDto();
        queryRequest2.setCurrent(2);
        queryRequest2.setSize(3);
        
        PageResponseDto<AppDetailResponseDto> page2 = appService.listMyAppsByPage(queryRequest2, mockRequest);
        System.out.println("第 2 页: " + page2.getList().size() + " 条记录");
        
        Assertions.assertEquals(page1.getTotal(), page2.getTotal(), "总数应该相同");
        System.out.println("总共 " + page1.getTotal() + " 条记录");
    }

    @Test
    @Order(13)
    @DisplayName("13. 测试每页最多20条限制")
    void testPageSizeLimit() {
        System.out.println("\n===== 测试：每页最多20条限制 =====");
        
        AppPageQueryRequestDto queryRequest = new AppPageQueryRequestDto();
        queryRequest.setCurrent(1);
        queryRequest.setSize(100); // 尝试获取 100 条
        
        PageResponseDto<AppDetailResponseDto> pageResponse = appService.listMyAppsByPage(queryRequest, mockRequest);
        
        Assertions.assertTrue(pageResponse.getList().size() <= 20, "每页最多应该返回 20 条记录");
        System.out.println("请求 100 条，实际返回: " + pageResponse.getList().size() + " 条");
    }
}
