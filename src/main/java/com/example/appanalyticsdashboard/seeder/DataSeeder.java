package com.example.appanalyticsdashboard.seeder;

import com.example.appanalyticsdashboard.model.Event;
import com.example.appanalyticsdashboard.model.Screen;
import com.example.appanalyticsdashboard.model.Session;
import com.example.appanalyticsdashboard.model.User;
import com.example.appanalyticsdashboard.repository.EventRepository;
import com.example.appanalyticsdashboard.repository.ScreenRepository;
import com.example.appanalyticsdashboard.repository.SessionRepository;
import com.example.appanalyticsdashboard.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final ScreenRepository screenRepository;
    private final EventRepository eventRepository;

    private final Random random = new Random();

    private final String[] platforms = {"Android", "iOS", "Web"};
    private final String[] eventTypes = {"click", "view", "purchase", "tap", "error", "scroll"};
    private final String[] screenNames = {"HomeScreen", "Checkout", "Profile", "ProductList", "Settings", "Onboarding"};

    public DataSeeder(UserRepository userRepository,
                      SessionRepository sessionRepository,
                      ScreenRepository screenRepository,
                      EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.screenRepository = screenRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            System.out.println("Data already seeded, skipping.");
            return;
        }

        List<Screen> screens = new ArrayList<>();
        for (String name : screenNames) {
            Screen screen = new Screen();
            screen.setScreenName(name);
            screen.getClass();
            screens.add(screenRepository.save(screen));
        }

        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            User user = new User();
            user.setUsername("user" + i);
            user.setDeviceId(UUID.randomUUID().toString());
            user.setPlatform(platforms[random.nextInt(platforms.length)]);
            user.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(60)));
            users.add(userRepository.save(user));
        }

        int totalEvents = 0;
        int targetEvents = 80;

        while (totalEvents < targetEvents) {
            User randomUser = users.get(random.nextInt(users.size()));
            LocalDateTime start = LocalDateTime.now()
                    .minusDays(random.nextInt(30))
                    .minusHours(random.nextInt(24));

            Session session = new Session();
            session.setUser(randomUser);
            session.setStartTime(start);
            session.setEndTime(start.plusMinutes(5 + random.nextInt(30)));
            session.setDeviceType(random.nextBoolean() ? "mobile" : "desktop");
            System.out.println("Seeded " + users.size() + " users and " + totalEvents + " events.");

            session = sessionRepository.save(session);

            int eventsInSession = 1 + random.nextInt(5);

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