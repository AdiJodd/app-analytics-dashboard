package com.example.appanalyticsdashboard.seeder;

import com.example.appanalyticsdashboard.model.*;
import com.example.appanalyticsdashboard.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private UserRepository userRepository;
    @Autowired private SessionRepository sessionRepository;
    @Autowired private ScreenRepository screenRepository;
    @Autowired private EventRepository eventRepository;

    private final Random random = new Random();

    private final String[] platforms = {"Android", "iOS", "Web"};
    private final String[] eventTypes = {"click", "view", "purchase", "tap", "error", "scroll"};
    private final String[] screenNames = {"HomeScreen", "Checkout", "Profile", "ProductList", "Settings", "Onboarding"};

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            System.out.println("Data already seeded, skipping.");
            return;
        }

        // 1. Seed Screens
        List<Screen> screens = new ArrayList<>();
        for (String name : screenNames) {
            Screen screen = new Screen();
            screen.setScreenName(name);
            screen.setScreenCategory(name.equals("Onboarding") ? "onboarding" : "core");
            screens.add(screenRepository.save(screen));
        }

        // 2. Seed Users (15 users)
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            User user = new User();
            user.setUsername("user" + i);
            user.setDeviceId(UUID.randomUUID().toString());
            user.setPlatform(platforms[random.nextInt(platforms.length)]);
            user.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(60)));
            users.add(userRepository.save(user));
        }

        // 3. Seed Sessions + Events (target ~80 events total)
        int totalEvents = 0;
        int targetEvents = 80;

        while (totalEvents < targetEvents) {
            User randomUser = users.get(random.nextInt(users.size()));

            Session session = new Session();
            session.setUser(randomUser);
            LocalDateTime start = LocalDateTime.now().minusDays(random.nextInt(30)).minusHours(random.nextInt(24));
            session.setStartTime(start);
            session.setEndTime(start.plusMinutes(5 + random.nextInt(30)));
            session.setDeviceType(random.nextBoolean() ? "mobile" : "desktop");
            session = sessionRepository.save(session);

            int eventsInSession = 1 + random.nextInt(5); // 1-5 events per session
            for (int j = 0; j < eventsInSession && totalEvents < targetEvents; j++) {
                Event event = new Event();
                event.setSession(session);
                event.setScreen(screens.get(random.nextInt(screens.size())));
                event.setEventType(eventTypes[random.nextInt(eventTypes.length)]);
                event.setEventLabel(eventTypes[random.nextInt(eventTypes.length)] + "_" + (j + 1));
                event.setTimestamp(start.plusMinutes(j * 2L));
                eventRepository.save(event);
                totalEvents++;
            }
        }

        System.out.println("Seeded " + users.size() + " users and " + totalEvents + " events.");
    }
}
 