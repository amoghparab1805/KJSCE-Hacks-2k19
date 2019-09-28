from django.contrib import admin
from .models import *
# Register your models here.

admin.site.register(EmailUser)
admin.site.register(PhoneUser)
admin.site.register(GoogleUser)
