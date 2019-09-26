from django.db import models

class User(models.TimeStampModel):
    email = models.CharField(max_length=100)
    display_name = models.CharField(max_length=500)
    phone_number = models.CharField(max_length=20, null=True)