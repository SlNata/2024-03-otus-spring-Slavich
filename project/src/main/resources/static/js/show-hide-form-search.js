const btn = document.getElementById('but-search');

btn.addEventListener('click', () => {
  const form = document.getElementById('form-search');

  if (form.style.display === 'block') {
    // 👇️ this SHOWS the form
    form.style.display = 'none';
//    btn.style.fontStyle = 'normal';
    btn.classList.remove("chng"); // отмена стиля кнопки #but-search с классом chng в NavigateStyle.css
  } else {
    // 👇️ this HIDES the form
    form.style.display = 'block';
//    btn.style.fontStyle = 'italic';
    btn.classList.toggle("chng"); // применяет стиль в NavigateStyle.css для #but-search.chng span:after
  }
});