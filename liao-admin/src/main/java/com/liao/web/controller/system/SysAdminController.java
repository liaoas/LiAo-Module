package com.liao.web.controller.system;

import com.liao.common.annotation.Log;
import com.liao.common.annotation.SignatureValidation;
import com.liao.common.core.R;
import com.liao.common.enums.BusinessType;
import com.liao.common.utils.poi.ExcelUtil;
import com.liao.system.entity.SysAdmin;
import com.liao.system.services.SysAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 管理员 前端控制器
 * </p>
 *
 * @author LiAo
 * @since 2020-12-14
 */
@Validated
@RestController
@RequestMapping("/system/sys-admin")
@Api(tags = "管理员")
public class SysAdminController {

    @Autowired
    private SysAdminService sysAdminService;

    /**
     * 管理员登录
     *
     * @param adminAccount  账号
     * @param adminPassword 密码
     * @return 结果
     */
    @PostMapping("login")
    @ApiOperation("管理员登录")
    public R login(String adminAccount, String adminPassword) {
        return sysAdminService.login(adminAccount, adminPassword);
    }

    /**
     * 退出登录
     *
     * @param token token
     * @return 结果
     */
    @PostMapping("logout")
    @ApiOperation("管理员退出登录")
    public R logout(String token) {
        return sysAdminService.logout(token);
    }

    /**
     * 分页查询 排序
     *
     * @param recode 条件
     * @return 结果
     */
    @PostMapping("sel_page")
    @ApiOperation("分页、排序、动态条件")
    @SignatureValidation
    public R selPage(SysAdmin recode) {
        return sysAdminService.selPage(recode);
    }

    /**
     * 根据id查询数据
     *
     * @param id id
     * @return 结果
     */
    @PostMapping("sel_id")
    @ApiOperation("根据id查询数据")
    public R findById(Long id) {
        return sysAdminService.findById(id);
    }

    /**
     * 根据ids查询数据
     *
     * @param ids ids
     * @return 结果
     */
    @PostMapping("sel_ids")
    @ApiOperation("根据ids批量查询")
    public R findByIds(@RequestParam("ids") List<Long> ids) {
        return sysAdminService.findByIds(ids);
    }

    /**
     * 添加数据
     *
     * @param recode 添加参数
     * @return 结果
     */
    @PostMapping("add")
    @ApiOperation("添加数据")
    @Log(title = "管理员", businessType = BusinessType.INSERT)
    public R add(SysAdmin recode, @RequestParam(value = "roleId", required = false) Long roleId) {
        return sysAdminService.add(recode, roleId);
    }

    /**
     * 根据id修改
     *
     * @param recode 修改参数
     * @return 结果
     */
    @PostMapping("upd_id")
    @ApiOperation("根据id修改")
    @Log(title = "管理员", businessType = BusinessType.UPDATE)
    public R upd(SysAdmin recode, Long roleId) {
        return sysAdminService.updById(recode, roleId);
    }

    /**
     * 根据id删除
     *
     * @param id id
     * @return 结果
     */
    @PostMapping("del_id")
    @ApiOperation("根据id删除")
    @Log(title = "管理员", businessType = BusinessType.DELETE)
    public R delete(Long id) {
        return sysAdminService.delete(id);
    }

    /**
     * 根据ids批量删除
     *
     * @param ids id集合
     * @return 结果
     */
    @PostMapping("del_ids")
    @ApiOperation("根据ids批量删除")
    @Log(title = "管理员", businessType = BusinessType.DELETE)
    public R deletes(@RequestParam("ids") List<Long> ids) {
        return sysAdminService.deletes(ids);
    }

    /**
     * 下载模板
     *
     * @return 文件参数
     */
    @GetMapping("/importTemplate")
    public R importTemplate() {
        // 创建对象
        ExcelUtil<SysAdmin> util = new ExcelUtil<>(SysAdmin.class);
        // 返回文件生成结果
        return util.importTemplateExcel("用户数据");
    }

    /**
     * 导入数据
     *
     * @param file 文件
     * @return 结果
     */
    @PostMapping("/importData")
    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    public R importData(MultipartFile file, boolean isUpdate) {
        return R.success(sysAdminService.importData(file, isUpdate));
    }

    /**
     * 导出数据
     * @param user 条件
     * @return 结果
     */
    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R export(SysAdmin user)
    {
        List<SysAdmin> list = sysAdminService.selectUserList(user);
        ExcelUtil<SysAdmin> util = new ExcelUtil<>(SysAdmin.class);
        return util.exportExcel(list, "用户数据");
    }
}

