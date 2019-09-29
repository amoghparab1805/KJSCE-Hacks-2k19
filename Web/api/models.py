from django.db import models
from django_extensions.db.models import TimeStampedModel
from django.contrib.auth.models import AbstractUser

class User(AbstractUser):
    phone_number = models.CharField(max_length=20, null=True)
    provider_id = models.CharField(max_length=50)
    photo_url =  models.CharField(max_length=1024, null=True)


    def __str__(self):
        return self.first_name + " " + self.last_name