package khang.lor.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import khang.lor.entity.UserInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// Các annotation để hỗ trợ tự động tạo constructor, getter, setter (cần Lombok nếu dùng @Data, @NoArgsConstructor, @AllArgsConstructor)
// Nếu không muốn dùng Lombok, bạn phải viết constructor, getter, setter thủ công.
public class UserInfoUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String name; // Tương ứng username
    private String password;
    private List<GrantedAuthority> authorities;

    // Constructor nhận vào một đối tượng UserInfo
    public UserInfoUserDetails(UserInfo userInfo) {
        this.name = userInfo.getName();
        this.password = userInfo.getPassword();
        // Chuyển danh sách các vai trò (roles) sang GrantedAuthority
        this.authorities = Arrays.stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Trả về danh sách quyền của người dùng
        return authorities;
    }

    @Override
    public String getPassword() {
        // Trả về mật khẩu của người dùng
        return password;
    }

    @Override
    public String getUsername() {
        // Trả về tên đăng nhập của người dùng
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Tài khoản không bị hết hạn
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Tài khoản không bị khóa
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Chứng chỉ (mật khẩu) không bị hết hạn
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Tài khoản được kích hoạt
        return true;
    }
}
