from django.db import models
from django.contrib.auth.models import User
from django_extensions.db.models import TimeStampedModel

class SocialUser(TimeStampedModel):
    email = models.CharField(max_length=100, null=True)
    display_name = models.CharField(max_length=500, null=True)
    phone_number = models.CharField(max_length=20, null=True)
    photo_url =  models.CharField(max_length=1024, null=True)
    provided_id = models.CharField(max_length=50)
    # user = models.ForeignKey(User)