  // Lógica da Sidebar
        const menuBtn = document.getElementById('menu-btn');
        const sidebar = document.getElementById('sidebar');

        menuBtn.onclick = () => {
            sidebar.classList.toggle('active');
            menuBtn.innerText = sidebar.classList.contains('active') ? "✕" : "☰";
        };

        // Lógica do Chat e XP
        let xpAtual = 0;
        let nivelAtual = 99;

        function ganharXP() {
            xpAtual += 150;
            if(xpAtual >= 1000) {
                xpAtual = 0;
                nivelAtual++;
                document.getElementById('status-level').innerText = nivelAtual;
            }
            document.getElementById('status-xp').innerText = `${xpAtual} / 1000`;
        }

        function handleSend() {
            const input = document.getElementById('user-input');
            const text = input.value.trim();
            if(!text) return;

            addMessage(text, 'user');
            input.value = "";

            setTimeout(() => {
                const respostas = ["Sincronizando...", "Status Rank-S mantido.", "Comando aceito.", "Acesso concedido."];
                const res = respostas[Math.floor(Math.random() * respostas.length)];
                addMessage(`[SISTEMA]: ${res}`, 'bot');
                ganharXP();
            }, 600);
        }

        function addMessage(text, side) {
            const div = document.createElement('div');
            div.className = `message ${side}`;
            div.innerText = text;
            const box = document.getElementById('chat-box');
            box.appendChild(div);
            box.scrollTop = box.scrollHeight;
            return div;
        }

        document.getElementById('chat-toggle').onclick = () => document.getElementById('chat-wrapper').classList.toggle('hidden');
        document.getElementById('close-chat').onclick = () => document.getElementById('chat-wrapper').classList.add('hidden');
        document.getElementById('send-btn').onclick = handleSend;
        document.getElementById('user-input').onkeypress = (e) => { if(e.key === 'Enter') handleSend(); };
        const API_KEY = "AIzaSyAvZjqiDKUyZSnBxbNAoFgdkQP8MJoikS0"; // Coloque sua chave entre as aspas
const URL = `https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=${API_KEY}`;


const chatBox = document.getElementById('chat-box');
const userInput = document.getElementById('user-input');
const sendBtn = document.getElementById('send-btn');

// --- ENGENHARIA DE PROMPT (PERSONALIDADE) ---
const systemInstruction = "Você é uma assistente virtual, onde seu objetivo e explicar de forma clara e fazer o aluno pensar, mas pode conversar de forma bem huemana, podendo responder de forma descontraida e até falar sobre a vida caso a pessoa puxe o assunto e você se chama'MEI', mas não pode falar como 'Aqui é a Mei, sua assistente virtual gente boa, que adora uma explicação clara e um bom papo '";
let conversaCompleta = "";

// --- COMUNICAÇÃO COM A API DO GEMINI ---
async function getGeminiResponse(message) {
    // Constrói o contexto para a IA não esquecer quem ela é
    const promptFinal = conversaCompleta === "" 
        ? `${systemInstruction} \n\n [INICIO DA SESSÃO] Pergunta do Aluno: ${message}`
        : `${conversaCompleta} \n\n Aluno: ${message}`;

    const response = await fetch(URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            contents: [{ parts: [{ text: promptFinal }] }]
        })
    });

    if (!response.ok) throw new Error("Falha na conexão com o Servidor");

    const data = await response.json();
    const botText = data.candidates[0].content.parts[0].text;

    // Atualiza o histórico para manter a memória da conversa
    conversaCompleta += `\nAluno: ${message} \nTutora: ${botText}`;
    
    return botText;
}

// --- INTERFACE E MENSAGENS ---
function addMessage(text, side) {
    const div = document.createElement('div');
    div.classList.add('message', side);
    
    // Adiciona prefixo estilo sistema se for o bot
    div.innerText = side === 'bot' ? `MEI: ${text}` : text;
    
    chatBox.appendChild(div);
    
    // Scroll suave para a última mensagem
    chatBox.scrollTo({
        top: chatBox.scrollHeight,
        behavior: 'smooth'
    });
}

async function handleSend() {
    const text = userInput.value.trim();
    if (!text) return;

    addMessage(text, 'user');
    userInput.value = '';
    
    // Feedback de carregamento estilo Solo Leveling
    addMessage("ANALISANDO DADOS...", 'bot');
    const loadingMsg = chatBox.lastElementChild;

    try {
        const botResponse = await getGeminiResponse(text);
        loadingMsg.innerText = `MEI: ${botResponse}`;
        
        // Pequeno efeito de som poderia ser inserido aqui
    } catch (error) {
        loadingMsg.innerText = "[ALERTA: FALHA NA CONEXÃO COM O MONSTRO DA REDE]";
        console.error(error);
    }
}

// --- EVENTOS DE CONTROLE ---
sendBtn.addEventListener('click', handleSend);
userInput.addEventListener('keypress', (e) => { 
    if (e.key === 'Enter') handleSend(); 
});