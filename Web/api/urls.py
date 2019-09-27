from django.urls import path
from .views import *

urlpatterns = [
    path('update-user-data/', UpdateUserData.as_view(), name='update_user_data'),
]