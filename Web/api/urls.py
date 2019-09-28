from django.urls import path
from .views import *

urlpatterns = [
    path('google-sign-up/', GoogleSignUp.as_view(), name='google_signup'),
    path('phone-sign-up/', PhoneSignUp.as_view(), name='phone_signup'),
    path('email-sign-up/', EmailSignUp.as_view(), name='email_signup'),
]