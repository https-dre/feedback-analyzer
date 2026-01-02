// seed.js
const API_URL = "http://localhost:8080/v1/batches";

const feedbacks = [
    "O sistema está incrivelmente rápido após a última atualização, parabéns!",
    "Não consigo gerar a nota fiscal do mês passado, o botão está cinza.",
    "A integração com o Slack parou de funcionar do nada hoje de manhã.",
    "Gostaria de saber se vocês têm planos para estudantes ou ONGs.",
    "Péssimo atendimento, fiquei 2 horas na fila do chat e ninguém respondeu.",
    "Meu cartão foi cobrado duas vezes esse mês, exijo estorno imediato.",
    "Adorei a nova funcionalidade de modo escuro, meus olhos agradecem.",
    "O app mobile fecha sozinho toda vez que tento anexar uma foto.",
    "Vocês podiam adicionar um gráfico de Gantt, faz muita falta.",
    "Cancelei minha conta mas continuam me mandando e-mails de marketing.",
    "O preço subiu demais, vou migrar para o concorrente se não tiver desconto.",
    "Muito bugado, perdi todos os dados da minha tarefa quando a internet caiu.",
    "A equipe de suporte foi super atenciosa e resolveu meu problema em 5 minutos.",
    "Não estou conseguindo convidar novos membros para o meu time.",
    "A API de vocês está retornando erro 500 em todas as requisições de relatório.",
    "Simplesmente o melhor software de gestão que já usei na vida.",
    "Onde eu troco a minha senha? A interface é muito confusa."
];

async function sendBatch() {
    console.log(`🚀 Preparando para enviar ${feedbacks.length} feedbacks...`);

    const payload = {
        tenantId: "taskflow-js-client",
        feedbacks: feedbacks
    };

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Erro na API: ${response.status} - ${errorText}`);
        }

        // Tenta ler o Location Header (onde está o recurso criado)
        const location = response.headers.get("Location");

        console.log("✅ Sucesso! Lote criado.");
        if (location) {
            console.log(`📍 Recurso disponível em: ${location}`);
        }

    } catch (error) {
        console.error("❌ Falha ao enviar:", error.message);
    }
}

sendBatch();