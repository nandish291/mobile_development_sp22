package com.example.numad22sp_nandishmurugeshi.atYourService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AtYourService extends AppCompatActivity {

    private List<EventCard> eventCards = new ArrayList<>();
    private EventView eventView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);
    }

    public void getEvents(View view) {
        eventCards = new ArrayList<>();
        EditText searchBox = findViewById(R.id.event_search);
        String searchTerm = searchBox.getText().toString();
        if (searchTerm.isEmpty()) {
            searchBox.setError("Search Box Cannot be empty");
        }
        else {
            ExecutorService service = Executors.newFixedThreadPool(1);
            List<Event> events = new ArrayList<>();
            Future<String> future = service.submit(new AsyncTask(searchTerm));
            String result;
            try {
                result = future.get(10, TimeUnit.SECONDS).toString();
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> map = objectMapper.readValue(result, Map.class);
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
                System.out.println(result);
            } catch (TimeoutException | InterruptedException | ExecutionException | IOException ex) {
                ex.printStackTrace();
            }
            for (Event e : events) {
                eventCards.add(new EventCard(e.title, e.performers.get(0).shortName, e.venue.name
                        , e.datetimeLocal, e.performers.get(0).image));
            }
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
}

class AsyncTask implements Callable<String> {

    String queryText;

    public AsyncTask(String queryText) {
        this.queryText = queryText;
    }

    @Override
    public String call() {
        StringBuilder content = new StringBuilder();
        try {
            String urlString = "https://api.seatgeek.com/2/events?q=" + queryText +
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
        return content.toString();
    }
}