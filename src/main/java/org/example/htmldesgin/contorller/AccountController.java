package org.example.htmldesgin.contorller;

import lombok.extern.slf4j.Slf4j;
import org.example.htmldesgin.service.AccountService;
import org.example.htmldesgin.service.TrainerWorkingHoursService;
import org.example.htmldesgin.utils.AccountUser;
import org.example.htmldesgin.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/account")
@Slf4j
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private TrainerWorkingHoursService trainerWorkingHoursService;

    @GetMapping("/account")
    public Result getAccount(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String telephone,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "false") Boolean search
    ) {
        if (!search) {
            // 不是搜索请求，忽略搜索参数
            return Result.success(accountService.findByPage(page, new HashMap<>()));
        }

        // 是搜索请求，包含搜索参数
        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("username", username);
        searchParams.put("telephone", telephone);
        searchParams.put("email", email);

        return Result.success(accountService.findByPage(page, searchParams));
    }

    @PostMapping("/add")
    public Result addAccount(@RequestBody AccountUser accountUser) {
        if (accountUser == null || accountUser.getUsername() == null) {
            return Result.error("用户名不能为空");
        }
        if (accountUser.getRole().equals("管理员")) {
            return accountService.addAccount(accountUser) == 1
                    ? Result.success("添加成功")
                    : Result.error("添加失败");
        }
        return accountService.addAccount(accountUser) == 1
                && trainerWorkingHoursService
                .addWorkingHours(accountUser.getUsername())
                ? Result.success("添加成功")
                : Result.error("添加失败");
    }

    @PostMapping("/update")
    public Result updateAccount(@RequestBody AccountUser accountUser) {
        return accountService.updateAccount(accountUser) == 1
                ? Result.success()
                : Result.error("更新失败");
    }

    @DeleteMapping("/delete")
    public Result deleteAccount(@RequestBody Integer[] ids) {
        int count = accountService.deleteAccount(ids);
        return count > 0
                && trainerWorkingHoursService
                .deleteWorkingHours(accountService.findUsernameByIds(ids))
                ? Result.success("成功删除" + count + "条记录")
                : Result.error("删除失败");
    }
}
