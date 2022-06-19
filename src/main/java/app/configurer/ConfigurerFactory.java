package app.configurer;

import java.util.Optional;

public class ConfigurerFactory {

    public Optional<Configurer> createConfigurer(int command) {
        Configurer configurer;
        switch (command) {
            case 1:
                configurer = new TelegramConfigurer();
                break;
            case 2:
                configurer = new EmailConfigurer();
                break;
            default:
                configurer = null;
        }
        return Optional.ofNullable(configurer);
    }
}
