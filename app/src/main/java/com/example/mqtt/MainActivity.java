package com.example.mqtt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
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
    public static MqttHandler mqttHandler;
    private TextView mqttMessageView;
    private Button sendButton;
    private Switch SwitchMod;

    private EditText textToSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mqttMessageView = findViewById(R.id.mqtt_message_view);
        textToSend = findViewById(R.id.text_to_send);
        sendButton = findViewById(R.id.send_button);
        SwitchMod = findViewById(R.id.Mode_Switch);

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

        SwitchMod.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Intent intent = new Intent(MainActivity.this, msgActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
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