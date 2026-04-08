// ── GERENCIAMENTO DE ABAS ──
const tabBtns = document.querySelectorAll('.tab-btn');
const tabPanels = document.querySelectorAll('.tab-panel');

tabBtns.forEach(btn => {
  btn.addEventListener('click', () => {
    const target = btn.dataset.tab;

    // Remove a classe 'active' de todos os botões e painéis
    tabBtns.forEach(b => b.classList.remove('active'));
    tabPanels.forEach(p => p.classList.remove('active'));

    // Adiciona a classe 'active' ao botão clicado e ao painel correspondente
    btn.classList.add('active');
    document.getElementById(`tab-${target}`).classList.add('active');
  });
});

// ── SISTEMA DE TOAST (FEEDBACK) ──
function showToast(message) {
  const toast = document.getElementById('toast');
  toast.textContent = message;
  toast.classList.add('show');

  // Esconde o toast após 3 segundos
  setTimeout(() => {
    toast.classList.remove('show');
  }, 3000);
}

// ── SIMULAÇÃO DE SALVAMENTO ──
const btnSave = document.getElementById('btnSave');
if (btnSave) {
  btnSave.addEventListener('click', async () => {
    // Altera o estado do botão para simular carregamento
    btnSave.textContent = '⏳ Salvando...';
    btnSave.disabled = true;
    btnSave.style.opacity = '0.7';
    let birthdateRaw = document.getElementById('inputBirthdate')?.value || "";
    let formattedDate = birthdateRaw;
    if (birthdateRaw.includes('/')) {
      const parts = birthdateRaw.split('/');
      if (parts.length === 3) {
        // Assume DD/MM/YYYY and convert to YYYY-MM-DD
        formattedDate = `${parts[2]}-${parts[1]}-${parts[0]}`;
      }
    }

    const dadosAtuais = {
      name: document.getElementById('inputName')?.value,
      dataNascimento: formattedDate,
      nacionalidade: document.getElementById('inputNationality')?.value,
      numTelefone: document.getElementById('inputPhone')?.value,
      cidade: document.getElementById('inputCity')?.value,
      bio: document.getElementById('inputBio')?.value,
      assento: document.getElementById('inputAssento')?.value,
      comida: document.getElementById('inputComida')?.value,
      classe: document.getElementById('inputClasse')?.value,
      moeda: document.getElementById('inputMoeda')?.value
    };

    try {
      const response = await fetch("/profile/user", {
        method: "PATCH",
        credentials: "include",
        body: JSON.stringify(dadosAtuais)
      });

      if (response.status === 200) {
        showToast('✅ Perfil atualizado com sucesso!');
      } else if (response.status === 401) {
        showToast('❌ Não autorizado. Faça login novamente.');
        setTimeout(() => window.location.href = '/login', 1500);
      } else if (response.status === 404) {
        showToast('❌ Usuário não encontrado.');
      } else {
        showToast('❌ Erro ao atualizar perfil.');
      }
    } catch (error) {
      console.error("Erro ao salvar:", error);
      showToast('❌ Erro de rede ao salvar perfil.');
    } finally {
      // Restaura o botão
      btnSave.textContent = '💾 Salvar Alterações';
      btnSave.disabled = false;
      btnSave.style.opacity = '1';
    }
  });
}

// ── OUTRAS INTERAÇÕES (com verificação de existência) ──
const btnAvatar = document.getElementById('btnAvatar');
if (btnAvatar) {
  btnAvatar.addEventListener('click', () => {
    showToast('📸 Função de upload de foto em breve!');
  });
}

const btnOutline = document.querySelector('.btn-outline');
if (btnOutline) {
  btnOutline.addEventListener('click', () => {
    showToast('Alterações descartadas.');
  });
}

// ── MENU MOBILE (HAMBURGER / SIDEBAR TOGGLE) ──
const hamburgerBtn = document.getElementById('hamburgerBtn');
const sidebar = document.getElementById('sidebar');
const sidebarOverlay = document.getElementById('sidebarOverlay');

function openSidebar() {
  if (!sidebar || !sidebarOverlay) return;
  sidebar.classList.add('open');
  sidebarOverlay.classList.add('visible');
  document.body.style.overflow = 'hidden';
}

function closeSidebar() {
  if (!sidebar || !sidebarOverlay) return;
  sidebar.classList.remove('open');
  sidebarOverlay.classList.remove('visible');
  document.body.style.overflow = '';
}

if (hamburgerBtn) {
  hamburgerBtn.addEventListener('click', () => {
    if (sidebar.classList.contains('open')) {
      closeSidebar();
    } else {
      openSidebar();
    }
  });
}

if (sidebarOverlay) {
  sidebarOverlay.addEventListener('click', closeSidebar);
}

// Fecha o sidebar ao clicar em um item de navegação (no mobile)
if (sidebar) {
  sidebar.querySelectorAll('.nav-item').forEach(item => {
    item.addEventListener('click', () => {
      if (window.innerWidth <= 768) {
        closeSidebar();
      }
    });
  });
}

// Fecha o sidebar ao redimensionar para desktop
window.addEventListener('resize', () => {
  if (window.innerWidth > 768) {
    closeSidebar();
  }
});

// ── LOADING USER DATA FROM BACKEND ──
document.addEventListener('DOMContentLoaded', async () => {

  try {
    // Altere este endpoint para a URL correta da sua API no backend.
    // A API deve retornar um JSON com os dados do usuário, por exemplo:
    // { "name": "João da Silva", "email": "joao@email.com", ... }
    const response = await fetch('/profile/user');

    if (response.ok) {
      const userData = await response.json();

      // Atualiza textos do container "hero" do perfil
      if (userData.name) {
        const heroName = document.getElementById('heroName');
        if (heroName) heroName.textContent = userData.name;

        // Atualiza campos de Input form
        const inputName = document.getElementById('inputName');
        if (inputName) inputName.value = userData.name;

        // Gera iniciais para o avatar
        const nameParts = userData.name.trim().split(' ');
        let initials = '?';
        if (nameParts.length >= 2) {
          initials = nameParts[0].charAt(0) + nameParts[nameParts.length - 1].charAt(0);
        } else if (nameParts.length === 1 && nameParts[0].length > 0) {
          initials = nameParts[0].substring(0, 2);
        }
        initials = initials.toUpperCase();

        const topbarAvatar = document.querySelector('.topbar-avatar');
        const heroInitials = document.getElementById('heroInitials');
        if (topbarAvatar) topbarAvatar.textContent = initials;
        if (heroInitials) heroInitials.textContent = initials;
      }

      if (userData.email) {
        const heroEmail = document.getElementById('heroEmail');
        if (heroEmail) heroEmail.textContent = userData.email;
      }

      // Aqui mapeamos os outros atributos do usuário para os campos
      if (userData.dataNascimento) {
        const birthdateInput = document.getElementById('inputBirthdate');
        if (birthdateInput) {
          if (userData.dataNascimento.includes('-')) {
            const parts = userData.dataNascimento.split('-');
            if (parts.length === 3) {
              birthdateInput.value = `${parts[2]}/${parts[1]}/${parts[0]}`;
            } else {
              birthdateInput.value = userData.dataNascimento;
            }
          } else {
            birthdateInput.value = userData.dataNascimento;
          }
        }
      }
      
      if (userData.nacionalidade) {
        const natInput = document.getElementById('inputNationality');
        if (natInput) natInput.value = userData.nacionalidade;
      }
      if (userData.numTelefone) {
        const phoneInput = document.getElementById('inputPhone');
        if (phoneInput) phoneInput.value = userData.numTelefone;
      }
      if (userData.cidade) {
        const cityInput = document.getElementById('inputCity');
        if (cityInput) cityInput.value = userData.cidade;
      }
      if (userData.bio) {
        const bioInput = document.getElementById('inputBio');
        if (bioInput) bioInput.value = userData.bio;
      }
      if (userData.assento) {
        const assentoInput = document.getElementById('inputAssento');
        if (assentoInput) assentoInput.value = userData.assento;
      }
      if (userData.comida) {
        const comidaInput = document.getElementById('inputComida');
        if (comidaInput) comidaInput.value = userData.comida;
      }
      if (userData.classe) {
        const classeInput = document.getElementById('inputClasse');
        if (classeInput) classeInput.value = userData.classe;
      }
      if (userData.moeda) {
        const moedaInput = document.getElementById('inputMoeda');
        if (moedaInput) moedaInput.value = userData.moeda;
      }

    } else {
      console.warn('Endpoint de usuário retornou erro:', response.status);
    }
  } catch (err) {
    console.error('Erro de rede ao buscar dados do usuário:', err);
  }
});