from __future__ import division

# P = successful_packets / total_transmissions
P = 3702 / 4753
print("P: %f" %(P))

# ber = 1 - P ^ 1/L where L is the length of the message in bits (16 bytes)
ber = 1 - P**(1/float(16*8))
print("BER: %f" %(ber))
