package br.com.hamburgueriaz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNomeCliente;
    private TextView tvQuantidadeHamburguer, tvPrecoTotal;
    private Button btnAdicionar, btnSubtrair, btnEnviarPedido;
    private CheckBox cbBacon, cbQueijo, cbMolho, cbCebola, cbAlface;

    private int quantidade = 0;
    private final double precoHamburguer = 15.00;
    private final double precoBacon = 3.00;
    private final double precoQueijo = 2.50;
    private final double precoMolho = 1.50;
    private final double precoCebola = 2.00;
    private final double precoAlface = 2.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNomeCliente = findViewById(R.id.et_nome_cliente);
        tvQuantidadeHamburguer = findViewById(R.id.tv_quantidade_hamburguer);
        tvPrecoTotal = findViewById(R.id.tv_preco_total);
        btnAdicionar = findViewById(R.id.btn_adicionar);
        btnSubtrair = findViewById(R.id.btn_subtrair);
        btnEnviarPedido = findViewById(R.id.btn_enviar_pedido);
        cbBacon = findViewById(R.id.cb_bacon);
        cbQueijo = findViewById(R.id.cb_queijo);
        cbMolho = findViewById(R.id.cb_molho);
        cbCebola = findViewById(R.id.cb_cebola);
        cbAlface = findViewById(R.id.cb_alface);

        // Listeners dos botões de quantidade
        btnAdicionar.setOnClickListener(v -> {
            quantidade++;
            atualizarPreco();
        });

        btnSubtrair.setOnClickListener(v -> {
            if (quantidade > 0) {
                quantidade--;
            }
            atualizarPreco();
        });

        // Listeners para os checkboxes de ingredientes
        View.OnClickListener checkBoxListener = v -> atualizarPreco();
        cbBacon.setOnClickListener(checkBoxListener);
        cbQueijo.setOnClickListener(checkBoxListener);
        cbMolho.setOnClickListener(checkBoxListener);
        cbCebola.setOnClickListener(checkBoxListener);
        cbAlface.setOnClickListener(checkBoxListener);

        // Botão de envio do pedido
        btnEnviarPedido.setOnClickListener(v -> {
            String nomeCliente = etNomeCliente.getText().toString().trim();
            if (nomeCliente.isEmpty()) {
                etNomeCliente.setError("Digite seu nome!");
                return;
            }

            if (quantidade == 0) {
                tvPrecoTotal.setText("Escolha pelo menos 1 hambúrguer!");
                return;
            }

            String mensagem = "Pedido de " + nomeCliente + ":\n"
                    + quantidade + " Hambúrguer(es)\n"
                    + (cbBacon.isChecked() ? "- Bacon\n" : "")
                    + (cbQueijo.isChecked() ? "- Queijo extra\n" : "")
                    + (cbMolho.isChecked() ? "- Molho especial\n" : "")
                    + (cbCebola.isChecked() ? "- Cebola crispy\n" : "")
                    + (cbAlface.isChecked() ? "- Alface e tomate\n" : "")
                    + "Total: R$ " + String.format("%.2f", calcularPreco());

            tvPrecoTotal.setText(mensagem);
        });
    }

    // Atualiza a quantidade e o preço total
    private void atualizarPreco() {
        tvQuantidadeHamburguer.setText(String.valueOf(quantidade));

        if (quantidade == 0) {
            tvPrecoTotal.setText("Preço total: R$ 0,00");
        } else {
            tvPrecoTotal.setText("Preço total: R$ " + String.format("%.2f", calcularPreco()));
        }
    }

    // Calcula o preço total com base na quantidade e adicionais
    private double calcularPreco() {
        if (quantidade == 0) return 0.00;

        double precoTotal = quantidade * precoHamburguer;

        if (cbBacon.isChecked()) precoTotal += precoBacon * quantidade;
        if (cbQueijo.isChecked()) precoTotal += precoQueijo * quantidade;
        if (cbMolho.isChecked()) precoTotal += precoMolho * quantidade;
        if (cbCebola.isChecked()) precoTotal += precoCebola * quantidade;
        if (cbAlface.isChecked()) precoTotal += precoAlface * quantidade;

        return precoTotal;
    }
}
