package com.example.numad22sp_nandishmurugeshi.atYourService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.numad22sp_nandishmurugeshi.R;
import com.example.numad22sp_nandishmurugeshi.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AtYourService extends AppCompatActivity {

    private List<EventCard> eventCards;
    private ProgressBar progressBar;

    public AtYourService() {
        this.eventCards = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        init(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Size", eventCards.size());
        for (int i = 0; i < eventCards.size(); i++) {
            outState.putString("eventName" + i, eventCards.get(i).getEventName());
            outState.putString("venue" + i, eventCards.get(i).getVenue());
            outState.putString("date" + i, eventCards.get(i).getDate().toString());
            outState.putString("image" + i, eventCards.get(i).getImage());
        }
    }

    private void init(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("Size")) {
            progressBar.setVisibility(View.VISIBLE);
            int size = savedInstanceState.getInt("Size");

            for (int i = 0; i < size; i++) {
                EventCard card = new EventCard(savedInstanceState.getString("eventName" + i),
                        savedInstanceState.getString("venue" + i),
                        new Date(savedInstanceState.getString("date" + i)),
                        savedInstanceState.getString("image" + i));
                eventCards.add(card);
            }
            createView();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getEvents(View view) throws InterruptedException {
        eventCards = new ArrayList<>();
        EditText searchBox = findViewById(R.id.event_search);
        String searchTerm = searchBox.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        if (searchTerm.isEmpty()) {
            searchBox.setError("Search Box Cannot be empty");
        } else {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder content = new StringBuilder();
                    try {
                        String urlString = "https://api.seatgeek.com/2/events?q=" + searchTerm +
                                "&client_id=" +
                                "MjE2OTUwODJ8MTYxODA4NjcyNS44MzkzNTc2";
                        URL url = new URL(urlString);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setDoInput(true);
                        con.setRequestMethod("GET");
                        con.setConnectTimeout(5000);
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                        in.close();
                        con.disconnect();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    populateEvents(content.toString());
                }
            });
            thread.start();
            System.out.println("Thread Still Running");
            thread.join();
            progressBar.setVisibility(View.GONE);
            createView();
        }
    }

    private void createView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.events_recycler_view);
        recyclerView.setHasFixedSize(true);
        EventView eventView = new EventView(eventCards);
        recyclerView.setAdapter(eventView);
        recyclerView.setLayoutManager(layoutManager);
    }

    void populateEvents(String result) {
        List<Event> events = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = null;
        try {
            map = objectMapper.readValue(result, Map.class);
            for (Map.Entry<String, Object> eventsMap : map.entrySet()) {
                if (eventsMap.getKey().equals("events")) {
                    ArrayList<Object> arrayList = (ArrayList<Object>) eventsMap.getValue();
                    for (int i = 0; i < arrayList.size(); i++) {
                        Map<String, Object> mapTemp = (Map<String, Object>) arrayList.get(i);
                        JSONObject jsonObject = new JSONObject(mapTemp);
                        events.add(objectMapper.readValue(jsonObject.toString(), Event.class));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Event e : events) {
            eventCards.add(new EventCard(e.title, e.venue.name
                    , e.datetimeLocal, e.performers.get(0).image));
        }
    }
}