package com.example.mqtt;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String BROKER_URL = "tcp://broker.hivemq.com:1883";
    private static final String Client_ID = "android-client_test_fdsadfawhd";
    private MqttHandler mqttHandler;
    private TextView mqttMessageView;
    private Button sendButton;
    private EditText textToSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mqttMessageView = findViewById(R.id.mqtt_message_view);
        textToSend = findViewById(R.id.text_to_send);
        sendButton = findViewById(R.id.send_button);
        mqttHandler = new MqttHandler();
        mqttHandler.setMessageCallback((topic, message) -> {
            runOnUiThread(() -> mqttMessageView.setText("Topic: " + topic + "\nMessage: " + message));
        });
        mqttHandler.connect(BROKER_URL,Client_ID);
        if (mqttHandler.isConnected()) {
            Toast.makeText(this,"Connected to broker",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Failed to connect to broker",Toast.LENGTH_SHORT).show();
        }
        SubscribeToTopic("testtopic/t2");
        SubscribeToTopic("testtopic/t3");
//        publishMessage("testtopic/t1","asd");S
        sendButton.setOnClickListener(v -> {
            String message = textToSend.getText().toString();
            publishMessage("testtopic/t3",message);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mqttHandler.disconnect();
    }
    private void publishMessage(String topic, String message) {
        mqttHandler.publish(topic, message);
        Toast.makeText(this,"Message published",Toast.LENGTH_SHORT).show();
    }
    private void SubscribeToTopic(String topic) {
        mqttHandler.subscribe(topic);
//        runOnUiThread(() -> mqttMessageView.setText("Subscribed to topic: " + topic));
        Toast.makeText(this,"Subscribed to topic",Toast.LENGTH_SHORT).show();
    }
}