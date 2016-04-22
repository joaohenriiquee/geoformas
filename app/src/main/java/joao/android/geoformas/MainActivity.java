package joao.android.geoformas;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    int countDrag = 0;

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
        findViewById(R.id.corVerde).setOnLongClickListener(new MyOnLongClickListener());


//         Campo para soltar.
        findViewById(R.id.cor).setOnDragListener(new MyOnDragListener(1));

//         Nomes
        findViewById(R.id.losango).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.triangulo).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.circulo).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.quadrado).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.retangulo).setOnLongClickListener(new MyOnLongClickListener());

//         Campo para soltar.
        findViewById(R.id.nome).setOnDragListener(new MyOnDragListener(1));
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
                        emissor.setVisibility(emissor.INVISIBLE);
                        emissor.setBackground(getDrawable(R.drawable.bg_vazio));
                        emissor.setElevation(0);
                        countDrag++;

                        ImageView figura = (ImageView) findViewById(R.id.figura);
                        TextView nome = (TextView) findViewById(R.id.nome);
                        TextView cor = (TextView) findViewById(R.id.cor);

                        switch (countDrag) {
                            case 2:
                                Log.i("Script", " - Completou 1");
                                TextView retangulo = (TextView) findViewById(R.id.retangulo);
                                figura.setImageDrawable(getDrawable(R.drawable.retanguloverde));
                                nome.setTag(retangulo.getTag());
                                nome.setText("");
                                cor.setTag(retangulo.getTag());
                                cor.setText("");
                                break;

                            case 4:
                                Log.i("Script", " - Completou 2");
                                TextView triangulo = (TextView) findViewById(R.id.triangulo);
                                figura.setImageDrawable(getDrawable(R.drawable.trianguloamarelo));
                                nome.setTag(triangulo.getTag());
                                nome.setText("");
                                cor.setTag(triangulo.getTag());
                                cor.setText("");
                                break;

                            case 6:
                                Log.i("Script", " - Completou 3");
                                TextView losango = (TextView) findViewById(R.id.losango);
                                figura.setImageDrawable(getDrawable(R.drawable.losangovermelho));
                                nome.setTag(losango.getTag());
                                nome.setText("");
                                cor.setTag(losango.getTag());
                                cor.setText("");
                                break;

                            case 8:
                                Log.i("Script", " - Completou 4");
                                TextView circulo = (TextView) findViewById(R.id.circulo);
                                figura.setImageDrawable(getDrawable(R.drawable.circulopreto));
                                nome.setTag(circulo.getTag());
                                nome.setText("");
                                cor.setTag(circulo.getTag());
                                cor.setText("");
                                break;
                        }

                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;
                        if (countDrag < 9) {
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


