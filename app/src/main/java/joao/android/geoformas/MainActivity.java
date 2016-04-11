package joao.android.geoformas;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.novojogo);
        button.setVisibility(button.INVISIBLE);

//         Cores
        findViewById(R.id.corAmarela).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.corVermelha).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.corPreta).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.corAzul).setOnLongClickListener(new MyOnLongClickListener());
//         Campo para soltar.
        findViewById(R.id.trianguloCor).setOnDragListener(new MyOnDragListener(1));
        findViewById(R.id.losangoCor).setOnDragListener(new MyOnDragListener(2));
        findViewById(R.id.circuloCor).setOnDragListener(new MyOnDragListener(3));
        findViewById(R.id.quadradoCor).setOnDragListener(new MyOnDragListener(4));

//         Nomes
        findViewById(R.id.losango).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.triangulo).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.circulo).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.quadrado).setOnLongClickListener(new MyOnLongClickListener());
//         Campo para soltar.
        findViewById(R.id.losangoNome).setOnDragListener(new MyOnDragListener(1));
        findViewById(R.id.nometrianguloamarelo).setOnDragListener(new MyOnDragListener(2));
        findViewById(R.id.nomecirculopreto).setOnDragListener(new MyOnDragListener(3));
        findViewById(R.id.quadradoNome).setOnDragListener(new MyOnDragListener(4));
    }

    public void submitNovoJogo(View view) {
        Intent novojogo = new Intent(this, MainActivity.class);
        startActivity(novojogo);
        super.onPause();
        finish();
    }

    class MyOnLongClickListener implements OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("simple_text", "text");
            DragShadowBuilder sb = new View.DragShadowBuilder(v);
            v.startDrag(data, sb, v, 0);
            v.setVisibility(View.INVISIBLE);
            return (true);
        }
    }

    class MyOnDragListener implements OnDragListener {
        private int num;

        public MyOnDragListener(int num) {
            super();
            this.num = num;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            TextView emissor = (TextView) event.getLocalState();
            TextView receptor = (TextView) findViewById(v.getId());

            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.i("Script", num + " - ACTION_DRAG_STARTED");
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        return (true);
                    }
                    return (false);
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.i("Script", num + " - ACTION_DRAG_ENTERED");
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.i("Script", num + " - ACTION_DRAG_LOCATION");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i("Script", num + " - ACTION_DRAG_EXITED");
                    break;
                case DragEvent.ACTION_DROP:
                    Log.i("Script", num + " - ACTION_DROP");
                    if (emissor.getTag().equals(receptor.getTag())) {
                        receptor.setText(emissor.getText());
                        emissor.setText("");
                        count++;

                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_LONG;
                        if (count < 8) {
                            CharSequence text = "Parabéns! Você acertou.";
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        } else {
                            CharSequence text = "Parabéns amiguinho, você completou tudo! :)";
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            Button button = (Button) findViewById(R.id.novojogo);
                            button.setVisibility(button.VISIBLE);
                        }
                        break;
                    } else {

                        Context context = getApplicationContext();
                        CharSequence text = "Tem certeza disso? Tente novamente.";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        break;
                    }

                case DragEvent.ACTION_DRAG_ENDED:
                    emissor.setVisibility(receptor.VISIBLE);
                    Log.i("Script", num + " - ACTION_DRAG_ENDED");
                    break;
            }
            return true;
        }
    }
}
