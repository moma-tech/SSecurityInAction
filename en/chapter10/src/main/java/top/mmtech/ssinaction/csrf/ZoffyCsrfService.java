package top.mmtech.ssinaction.csrf;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ZoffyCsrfService implements CsrfTokenRepository {

    @Autowired
    ZoffyCsrfTokenRepository zoffyCsrfTokenRepository;

    @Override
    public CsrfToken generateToken(final HttpServletRequest request) {
        final String token = UUID.randomUUID().toString();
        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token);
    }

    @Override
    public void saveToken(final CsrfToken token,
            final HttpServletRequest request,
            final HttpServletResponse response) {
        final String reIp = request.getRemoteAddr();
        final Optional<ZoffyCsrfToken> zoffyCsrfToken =
                this.zoffyCsrfTokenRepository.findByTokenHolder(reIp);
        if (zoffyCsrfToken.isPresent()) {
            final ZoffyCsrfToken zToken = zoffyCsrfToken.get();
            zToken.setCsrfToken(UUID.randomUUID().toString());
            zToken.setUpdateTime(LocalDateTime.now());
            this.zoffyCsrfTokenRepository.save(zToken);
        } else {
            this.zoffyCsrfTokenRepository.save(ZoffyCsrfToken.builder()
                    .tokenHolder(reIp).csrfToken(UUID.randomUUID().toString())
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now()).build());

        }
    }

    @Override
    public CsrfToken loadToken(final HttpServletRequest request) {
        final String reIp = request.getRemoteAddr();
        final Optional<ZoffyCsrfToken> zoffyCsrfToken =
                this.zoffyCsrfTokenRepository.findByTokenHolder(reIp);
        if(zoffyCsrfToken.isPresent()){
            return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", zoffyCsrfToken.get().getCsrfToken());
        }else{
            return null;
        }
    }

}
