# RMI_Chat_Cliente

RMI Chat foi um projeto desenvolvido como requisito avaliativo da disciplina de Computação distribuida.<br>

<strong>Objetivo:</strong> 
	<ul><li>Desenvolver uma sala de bate papo virtual utilizando Java RMI.</ul></li>
<br>

<strong>Requisitos: </strong>
<ul>
	<li>Possibilidade de mandar e receber mensagens de diversos usuários;</li>
	<li>Visualizar usuários online;</li>
	<li>Receber apenas as mensagens recebidas no momento que estiver online (não receber mensagens antigas);</li>
	<li>Possuir interface gráfica amigável para o usuário;</li>
	<li>Possibilidade de conversa individual ou com todos;</li>
</ul>
<br>

<strong>Observações:</strong>
<ul>
	<li>O cliente por padrão se conecta na porta 1090 do socket servidor;</li>
	<li>O servidor atualiza a lista de clientes online a cada 2 minutos por padrão;</li>
</ul>
<br>

<strong>Partes do Projeto: </strong>
<ul>
	<li><a href="https://github.com/TommyJando/RMI_Chat_Server">Servidor</a></li>
	<li><a href="https://github.com/TommyJando/RMI_Chat_Cliente">Cliente</a></li>
	<li><a href="https://github.com/TommyJando/RMI_Chat_Mensagem">Mensagem</a> (interface necessária para implementar no servidor e cliente)</li>
</ul>
