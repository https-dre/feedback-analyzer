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
    "Onde eu troco a minha senha? A interface é muito confusa.",
    "Recebi uma cobrança de R$ 200,00 mas meu plano é o de R$ 50,00.",
    "O sistema é bom, mas falta tradução para espanhol.",
    "Estou tentando fazer upgrade de plano mas dá erro no cartão.",
    "A funcionalidade de exportar para PDF vem toda desformatada.",
    "Quero parabenizar o time de UX, ficou muito intuitivo.",
    "Acho um absurdo não ter suporte telefônico para planos Enterprise.",
    "Meu gerente não consegue ver as tarefas que eu criei, urgentíssimo!",
    "Vocês oferecem algum desconto para pagamento anual antecipado?",
    "O sistema cai toda sexta-feira à tarde, impossível trabalhar assim.",
    "Achei a documentação da API muito fraca e desatualizada.",
    "Gostaria de sugerir uma integração nativa com o Google Calendar.",
    "Fui muito mal atendido pelo atendente chamado João.",
    "Resolvido! Era só limpar o cache, obrigado pela ajuda.",
    "Estou recebendo e-mails de notificação de tarefas de outra empresa!",
    "O layout quebrou no meu monitor ultrawide.",
    "Quanto custa para adicionar mais 10 usuários no meu plano?",
    "Não consigo recuperar minha senha, o e-mail nunca chega.",
    "Excelente custo-benefício comparado ao Jira.",
    "O carregamento da página inicial leva mais de 10 segundos.",
    "Quero cancelar, como faço? Não acho o botão.",
    "Vocês emitem nota fiscal para PJ?",
    "A busca não encontra tarefas antigas, parece que sumiram.",
    "Adorei os novos relatórios automáticos, economizei horas.",
    "O botão de salvar fica girando infinitamente e não salva nada.",
    "Preciso falar com o setor financeiro urgente.",
    "A cor do botão de excluir é muito parecida com o de salvar, quase fiz besteira.",
    "Vocês têm previsão de lançar aplicativo para Linux?",
    "Minha equipe está amando usar a ferramenta, aumentou nossa produtividade.",
    "O boleto venceu e não consigo gerar a segunda via.",
    "Trava muito no Firefox, só funciona bem no Chrome.",
    "Gostaria de uma demonstração guiada para minha diretoria.",
    "O sistema diz que meu e-mail já está em uso, mas nunca me cadastrei.",
    "Simples, eficiente e barato. Recomendo para todos."
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