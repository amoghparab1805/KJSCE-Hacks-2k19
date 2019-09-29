from rest_framework import serializers
from. models import *

class EmailSignUpSerializer(serializers.ModelSerializer):
    password = serializers.CharField(
        write_only=True,
        required=True,
        style={'input_type': 'password'},
    )
    class Meta:
        model = EmailUser
        fields = (
            "display_name",
            "email",
            "password",
        )