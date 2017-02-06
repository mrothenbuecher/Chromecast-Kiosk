(function() {
    
	
	var set_locale_to = function(locale) {
	      if (locale)
	        $.i18n().locale = locale;
	 };
	
    $.i18n().load({
        de: 'assets/lang/de.json',
        en: 'assets/lang/en.json'
    }).done(function() {
    	set_locale_to(url('?locale'));
    	History.Adapter.bind(window, 'statechange', function(){
            set_locale_to(url('?locale'));
          });
        console.log('lang download done!');
        init('body');
    });

    function init(selector) {
        $(selector).i18n();
        $(selector).find("[data-i18n-placeholder]").each(function(){
        	$(this).attr('placeholder', $.i18n($(this).data('i18n-placeholder')));
        });
        console.log('lang init done!');
    }

    $(document).ready(function() {
        $('input[name="lang"]').on("change", function() {
            $this = $(this);
            console.log("change lang to: "+$this.val());
            History.pushState(null, null, "?locale=" + $this.val());
            init('body');
        });
    });
})();
