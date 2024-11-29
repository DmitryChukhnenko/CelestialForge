package Dmitry.CelestialForge.Session;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {
    private String data;
}
