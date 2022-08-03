package liteplus.utils;

import java.net.Proxy;
import java.util.Optional;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;

public final class LoginManager {

    public static String login(String email, String password) {
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(
                Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);

        auth.setUsername(email);
        auth.setPassword(password);

        try {
            if (email.isEmpty())
                return "\u00a7cNo Username/Email!";

            if (password.isEmpty()) {
                FabricReflect.writeField(MinecraftClient.getInstance().getSession(), email, "a", "username");
                return "\u00a76Logged in as an unverified account"; /* Idk this sound weird */
            }

            if (!email.isEmpty() && !password.isEmpty())
                auth.logIn();


            Session newsession = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), Optional.of("mojang"), null, Session.AccountType.MOJANG);
            FabricReflect.writeField(MinecraftClient.getInstance(), newsession, "field_1726", "session");
            return "\u00a7aLogin Successful";

        } catch (SecurityException e) {
            return "\u00a7cReflection Error";

        } catch (AuthenticationException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Invalid username or password.")
                    || e.getMessage().toLowerCase().contains("account migrated"))
                return "\u00a74Wrong password!";
            else
                return "\u00a7cCannot contact authentication server!";

        } catch (NullPointerException e) {
            return "\u00a74Wrong password!";

        }
    }
}