from django.db import models
from django_extensions.db.models import TimeStampedModel

class EmailUser(TimeStampedModel):
    display_name = models.CharField(max_length=100, blank=True)
    email = models.CharField(max_length=100, null=True, unique=True)
    password = models.CharField(max_length=50)

class PhoneUser(TimeStampedModel):
    display_name = models.CharField(max_length=500, null=True)
    phone_number = models.CharField(max_length=20, null=True, unique=True)
    photo_url =  models.CharField(max_length=1024, null=True)
    provider_id = models.CharField(max_length=50)

class GoogleUser(TimeStampedModel):
    email = models.CharField(max_length=100, null=True, unique=True)
    display_name = models.CharField(max_length=500, null=True)
    photo_url =  models.CharField(max_length=1024, null=True)
    provider_id = models.CharField(max_length=50)